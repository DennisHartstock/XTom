using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

using Google.Protobuf.Collections;
using Google.Protobuf.WellKnownTypes;

using XTom_Studio.Core.Contracts.Backbone;
using XTom_Studio.Core.Contracts.Communication;

using XTomRemoteClient;

namespace GrpcRemoteClient;
public class GrpcRemoteExecution : IRemoteExecution
{
    private readonly GrpcRemoteChannel _remoteChannel;

    public GrpcRemoteExecution(GrpcRemoteChannel remoteChannel)
    {
        _remoteChannel = remoteChannel;
    }

    public bool CanExecute(IDeviceActionContext deviceContext) => true; // Bitte noch anpassen, nur vorübergehend!

    public async Task ExecuteAsync(IDeviceActionContext deviceContext, CancellationToken cancelToken)
    {
        var request = new ExecuteCommandRequest() { DeviceId = deviceContext.DeviceId, CommandName = deviceContext.DeviceActionName };

        await _remoteChannel.ExecuteCommandAsync(request);
    }

    public async Task ExecuteAsync<T1>(IDeviceActionContext deviceContext, T1 param1, CancellationToken cancelToken)
    {
        var request = new ExecuteCommandRequest() { DeviceId = deviceContext.DeviceId, CommandName = deviceContext.DeviceActionName };
        request.Parameters.Add(ConvertParameter(param1));

        await _remoteChannel.ExecuteCommandAsync(request);
    }

    public async Task ExecuteAsync<T1, T2>(IDeviceActionContext deviceContext, T1 param1, T2 param2, CancellationToken cancelToken)
    {
        var request = new ExecuteCommandRequest() { DeviceId = deviceContext.DeviceId, CommandName = deviceContext.DeviceActionName };
        request.Parameters.Add(ConvertParameter(param1));
        request.Parameters.Add(ConvertParameter(param2));

        await _remoteChannel.ExecuteCommandAsync(request);
    }

    public Task<Tout> ExecuteAsync<Tout>(IDeviceActionContext deviceContext, CancellationToken cancelToken) => throw new NotImplementedException();

    public Task<Tout> ExecuteAsync<T1, Tout>(IDeviceActionContext deviceContext, T1 param1, CancellationToken cancelToken) => throw new NotImplementedException();

    public Task<Tout> ExecuteAsync<T1, T2, Tout>(IDeviceActionContext deviceContext, T1 param1, T2 param2, CancellationToken cancelToken) => throw new NotImplementedException();

    private static DeviceActionParameter ConvertParameter<T>(T value) => value switch
    {
        double doubleValue => new DeviceActionParameter() { DoubleValue = doubleValue },
        float floatValue => new DeviceActionParameter() { FloatValue = floatValue },
        int intValue => new DeviceActionParameter() { Int32Value = intValue },
        long longValue => new DeviceActionParameter() { Int64Value = longValue },
        uint uintValue => new DeviceActionParameter() { Uint32Value = uintValue },
        ulong ulongValue => new DeviceActionParameter() { Uint64Value = ulongValue },
        bool boolValue => new DeviceActionParameter() { BoolValue = boolValue },
        string stringValue => new DeviceActionParameter() { StringValue = stringValue },
        _ => new DeviceActionParameter() { VoidValue = new Empty() }
    };
}
