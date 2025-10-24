using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

using Google.Protobuf.Collections;

using GrpcRemoteClient.Primitives;

using Microsoft.Extensions.Logging;

using XTom_Studio.Core.Contracts.Backbone;
using XTom_Studio.Core.Contracts.Communication;
using XTom_Studio.Core.Models;
using XTom_Studio.Core.Primitives;

using XTomRemoteClient;

namespace GrpcRemoteClient;
public class GrpcRemoteClient : IRemoteClient
{
    private readonly GrpcRemoteChannel _remoteChannel;
    private readonly IDeviceActionFactory _deviceActionFactory;
    private readonly IMonitorValueFactory _monitorValueFactory;
    private readonly ILogger<GrpcRemoteClient> _logger;

    public event EventHandler<ConfigurationChangedEventArgs>? ConfigurationChanged;

    public RemoteCtConfiguration? RemoteCtConfiguration
    {
        get; private set;
    }

    public GrpcRemoteClient(GrpcRemoteChannel remoteChannel, IDeviceActionFactory deviceActionFactory, IMonitorValueFactory monitorValueFactory, ILogger<GrpcRemoteClient> logger)
    {
        _remoteChannel = remoteChannel;
        _deviceActionFactory = deviceActionFactory;
        _monitorValueFactory = monitorValueFactory;
        _logger = logger;

        if (remoteChannel.SystemConfiguration != null)
            BuildCtConfiguration(remoteChannel.SystemConfiguration);

        remoteChannel.SystemConfigurationChanged += RemoteChannel_SystemConfigurationChanged;
    }

    private void BuildCtConfiguration(SystemConfigurationResponse systemConfiguration)
    {
        var configBuilder = new GrpcCtConfigurationBuilder(_deviceActionFactory, _monitorValueFactory);

        RemoteCtConfiguration = configBuilder.Build(systemConfiguration);
        ConfigurationChanged?.Invoke(this, new ConfigurationChangedEventArgs(RemoteCtConfiguration));
    }

    private void RemoteChannel_SystemConfigurationChanged(object? sender, SystemConfigurationChangedEventHandler e)
    {
        BuildCtConfiguration(e.SystemConfiguration);
    }
}
