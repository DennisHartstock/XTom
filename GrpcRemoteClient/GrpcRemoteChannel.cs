using Grpc.Net.Client;
using XTom_Studio.Core.Models;
using Grpc.Core;
using XTom_Studio.Core.Contracts.Communication;
using XTom_Studio.Core.Primitives;
using XTomRemoteClient;
using Microsoft.Extensions.Logging;
using XTom_Studio.Core.Contracts.Backbone;
using XTom_Studio.Core.Backbone;
using Google.Protobuf.WellKnownTypes;
using GrpcRemoteClient.Primitives;
using XTom_Studio.Core.Exceptions;
using System.Diagnostics.CodeAnalysis;
using System.Data;

namespace GrpcRemoteClient;

public class GrpcRemoteChannel : IRemoteChannel, IDisposable
{
    const string ClientNotConnectedErr = "Client is not connected to remote server.";
    private readonly string _connectionString;
    private readonly ILogger<GrpcRemoteChannel> _logger;
    private GrpcChannel? _grpcChannel;
    private XTomRemote.XTomRemoteClient? _remoteClient;
    private readonly CancellationTokenSource _tokenSource;
    private readonly CancellationToken _cancelToken;


    internal event EventHandler<SystemConfigurationChangedEventHandler>? SystemConfigurationChanged;
    internal event EventHandler<MonitorValueUpdateEventArgs>? MonitorValueUpdate;


    internal SystemConfigurationResponse? SystemConfiguration
    {
        get; private set;
    }

    public bool IsConnected
    {
        get; private set;
    }


    public IDeviceActionAsync ConnectAsyncCommand
    {
        get;
    }

    public IDeviceActionAsync DisconnectAsyncCommand
    {
        get;
    }

    public GrpcRemoteChannel(string connectionString, ILogger<GrpcRemoteChannel> logger)
    {
        _connectionString = connectionString;
        _logger = logger;

        var contextFactory = new DeviceActionContextFactory();
        ConnectAsyncCommand = new DeviceActionAsync(contextFactory.CreateContext(nameof(GrpcRemoteChannel), nameof(ConnectAsyncCommand)), _logger, ConnectAsync, CanConnectAsync);
        DisconnectAsyncCommand = new DeviceActionAsync(contextFactory.CreateContext(nameof(GrpcRemoteChannel), nameof(DisconnectAsyncCommand)), _logger, DisconnectAsync, CanDisconnectAsync);

        _tokenSource = new CancellationTokenSource();
        _cancelToken = _tokenSource.Token;
    }

    private async Task ConnectAsync(IDeviceActionContext deviceContext, CancellationToken token)
    {
        SystemConfiguration = null;

        _grpcChannel = GrpcChannel.ForAddress(_connectionString);
        _remoteClient = new XTomRemote.XTomRemoteClient(_grpcChannel);

        SystemConfigurationResponse systemConfiguration = await _remoteClient.GetSystemConfigurationAsync(new Empty());

        IsConnected = true;

        SystemConfiguration = systemConfiguration;
        SystemConfigurationChanged?.Invoke(this, new SystemConfigurationChangedEventHandler(systemConfiguration));
        RunTasks();
    }

    private void RunTasks()
    {
        Task.Run(GetMonitorValueUpdates, _cancelToken).ContinueWith(_ => { });
    }

    private bool CanConnectAsync(IDeviceActionContext deviceContext)
    {
        return true;
    }

    private Task DisconnectAsync(IDeviceActionContext deviceContext, CancellationToken token)
    {
        throw new NotImplementedException();
    }

    private bool CanDisconnectAsync(IDeviceActionContext deviceContext)
    {
        return true;
    }

    public async Task<ExecuteCommandResponse> ExecuteCommandAsync(ExecuteCommandRequest commandRequest)
    {
        if (_remoteClient == null)
            throw new RemoteConnectionException(ClientNotConnectedErr);

        return await _remoteClient.ExecuteCommandAsync(commandRequest);
    }

    public MonitorValueDescription GetMonitorValue(string deviceId, string monitorName)
    {
        if (_remoteClient == null)
            throw new RemoteConnectionException(ClientNotConnectedErr);

        return _remoteClient.GetMonitorValue(new MonitorValueRequest() { DeviceId = deviceId, MonitorName = monitorName });
    }

    public async Task<MonitorValueDescription> GetMonitorValueAsync(string deviceId, string monitorName)
    {
        if (_remoteClient == null)
            throw new RemoteConnectionException(ClientNotConnectedErr);

        return await _remoteClient.GetMonitorValueAsync(new MonitorValueRequest() { DeviceId = deviceId, MonitorName = monitorName });
    }

    private async void GetMonitorValueUpdates()
    {
        if (_remoteClient == null)
            return;

        using var call = _remoteClient.GetMonitorValueUpdates(new Empty());

        await foreach (var message in call.ResponseStream.ReadAllAsync(_cancelToken))
        {
            MonitorValueUpdate?.Invoke(this, new MonitorValueUpdateEventArgs(message));
        }
    }

    public void Dispose()
    {
        _tokenSource.Cancel();
    }
}
