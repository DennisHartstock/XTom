using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

using XTom_Studio.Core.Contracts.Backbone;
using XTom_Studio.Core.Contracts.Communication;
using XTom_Studio.Core.CtConfiguration;
using XTom_Studio.Core.Exceptions;
using XTom_Studio.Core.Models;

using XTomRemoteClient;

namespace GrpcRemoteClient;
internal class GrpcCtConfigurationBuilder
{
    const string AxisClassName = "Axis";
    private readonly IDeviceActionFactory _deviceActionFactory;
    private readonly IMonitorValueFactory _monitorValueFactory;

    public GrpcCtConfigurationBuilder(IDeviceActionFactory deviceActionFactory, IMonitorValueFactory monitorValueFactory)
    {
        _deviceActionFactory = deviceActionFactory;
        _monitorValueFactory = monitorValueFactory;
    }

    public RemoteCtConfiguration Build(SystemConfigurationResponse systemConfiguration)
    {
        string detectorId = systemConfiguration.DetectorId;
        string sourceId = systemConfiguration.SourceId;

        RemoteDeviceDescription? detectorDescription = null;
        RemoteDeviceDescription? sourceDescription = null;
        List<RemoteDeviceDescription> axesDescription = new List<RemoteDeviceDescription>();

        foreach (var device in systemConfiguration.Devices)
        {
            if (device.DeviceId == detectorId)
                detectorDescription = device;
            else if (device.DeviceId == sourceId)
                sourceDescription = device;
            else if (device.DeviceType == AxisClassName)
                axesDescription.Add(device);
        }

        if (detectorDescription == null)
            throw new RemoteConfigurationException("Could not find detector description in remote system configuration.");

        if (sourceDescription == null)
            throw new RemoteConfigurationException("Could not find source description in remote system configuration.");

        if (axesDescription.Count == 0)
            throw new RemoteConfigurationException("Could not find axes description in remote system configuration.");

        RemoteCtDetector ctDetector = BuildDetector(detectorDescription);
        RemoteCtSource ctSource = BuildSource(sourceDescription);
        Dictionary<string, RemoteCtAxis> ctAxes = BuildAxes(axesDescription);

        return new RemoteCtConfiguration(ctDetector, ctSource, ctAxes);
    }

    private RemoteCtDetector BuildDetector(RemoteDeviceDescription detectorDescription)
    {
        return new RemoteCtDetector(detectorDescription.DeviceId, _monitorValueFactory, _deviceActionFactory);
    }

    private RemoteCtSource BuildSource(RemoteDeviceDescription detectorDescription)
    {
        return new RemoteCtSource(detectorDescription.DeviceId, _monitorValueFactory, _deviceActionFactory);
    }

    private Dictionary<string, RemoteCtAxis> BuildAxes(List<RemoteDeviceDescription> axesDescription)
    {
        Dictionary<string, RemoteCtAxis> axesDict = new Dictionary<string, RemoteCtAxis>();

        foreach (RemoteDeviceDescription description in axesDescription)
            axesDict.Add(description.DeviceId, new RemoteCtAxis(description.DeviceId, _monitorValueFactory, _deviceActionFactory));

        return axesDict;
    }
}
