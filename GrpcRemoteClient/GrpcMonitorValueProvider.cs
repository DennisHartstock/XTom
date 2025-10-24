using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

using XTom_Studio.Core.Backbone;
using XTom_Studio.Core.Contracts.Communication;
using XTom_Studio.Core.Primitives;

using XTomRemoteClient;

namespace GrpcRemoteClient;
public class GrpcMonitorValueProvider : IMonitorValueProvider
{
    private readonly MonitorValueObersable<double> _doubleMonitorValue;
    private readonly MonitorValueObersable<float> _floatMonitorValue;
    private readonly MonitorValueObersable<int> _intMonitorValue;
    private readonly MonitorValueObersable<long> _longMonitorValue;
    private readonly MonitorValueObersable<uint> _uintMonitorValue;
    private readonly MonitorValueObersable<ulong> _ulongMonitorValue;
    private readonly MonitorValueObersable<bool> _boolMonitorValue;
    private readonly MonitorValueObersable<string> _stringMonitorValue;
    private readonly MonitorValueObersable<RemoteDeviceState> _devStateMonitorValue;
    private readonly MonitorValueObersable<RemoteWarmupState> _warmupStateMonitorValue;
    private readonly GrpcRemoteChannel _grpcChannel;

    public GrpcMonitorValueProvider(GrpcRemoteChannel grpcChannel)
    {
        _grpcChannel = grpcChannel;
        _grpcChannel.MonitorValueUpdate += GrpcChannel_MonitorValueUpdate;

        _doubleMonitorValue = new MonitorValueObersable<double>();
        _floatMonitorValue = new MonitorValueObersable<float>();
        _intMonitorValue = new MonitorValueObersable<int>();
        _longMonitorValue = new MonitorValueObersable<long>();
        _uintMonitorValue = new MonitorValueObersable<uint>();
        _ulongMonitorValue = new MonitorValueObersable<ulong>();
        _boolMonitorValue = new MonitorValueObersable<bool>();
        _stringMonitorValue = new MonitorValueObersable<string>();
        _devStateMonitorValue = new MonitorValueObersable<RemoteDeviceState>();
        _warmupStateMonitorValue = new MonitorValueObersable<RemoteWarmupState>();
    }

    public IDisposable Register<T>(string deviceId, string monitorName, IObserver<MonitorValueUpdate<T>> monitorValue)
    {
        MonitorValueDescription description = _grpcChannel.GetMonitorValue(deviceId, monitorName);

        switch (default(T))
        {
            case double:
                return _doubleMonitorValue.Subscribe(
                    deviceId,
                    monitorName,
                    (IObserver<MonitorValueUpdate<double>>)monitorValue,
                    new MonitorValueUpdate<double>(
                        description.MonitorValueDouble.Value,
                        description.MonitorValueDouble.ValueSetpoint,
                        description.MonitorValueDouble.MinValue,
                        description.MonitorValueDouble.MaxValue,
                        description.MonitorValueDouble.PhysicalMinValue,
                        description.MonitorValueDouble.PhysicalMaxValue));
            case float:
                return _floatMonitorValue.Subscribe(
                    deviceId,
                    monitorName,
                    (IObserver<MonitorValueUpdate<float>>)monitorValue,
                    new MonitorValueUpdate<float>(
                        description.MonitorValueFloat.Value,
                        description.MonitorValueFloat.ValueSetpoint,
                        description.MonitorValueFloat.MinValue,
                        description.MonitorValueFloat.MaxValue,
                        description.MonitorValueFloat.PhysicalMinValue,
                        description.MonitorValueFloat.PhysicalMaxValue));
            case int:
                return _intMonitorValue.Subscribe(
                    deviceId,
                    monitorName,
                    (IObserver<MonitorValueUpdate<int>>)monitorValue,
                    new MonitorValueUpdate<int>(
                        description.MonitorValueInt32.Value,
                        description.MonitorValueInt32.ValueSetpoint,
                        description.MonitorValueInt32.MinValue,
                        description.MonitorValueInt32.MaxValue,
                        description.MonitorValueInt32.PhysicalMinValue,
                        description.MonitorValueInt32.PhysicalMaxValue));
            case long:
                return _longMonitorValue.Subscribe(
                    deviceId,
                    monitorName,
                    (IObserver<MonitorValueUpdate<long>>)monitorValue,
                    new MonitorValueUpdate<long>(
                        description.MonitorValueInt64.Value,
                        description.MonitorValueInt64.ValueSetpoint,
                        description.MonitorValueInt64.MinValue,
                        description.MonitorValueInt64.MaxValue,
                        description.MonitorValueInt64.PhysicalMinValue,
                        description.MonitorValueInt64.PhysicalMaxValue));
            case uint:
                return _uintMonitorValue.Subscribe(
                    deviceId,
                    monitorName,
                    (IObserver<MonitorValueUpdate<uint>>)monitorValue,
                    new MonitorValueUpdate<uint>(
                        description.MonitorValueUint32.Value,
                        description.MonitorValueUint32.ValueSetpoint,
                        description.MonitorValueUint32.MinValue,
                        description.MonitorValueUint32.MaxValue,
                        description.MonitorValueUint32.PhysicalMinValue,
                        description.MonitorValueUint32.PhysicalMaxValue));
            case ulong:
                return _ulongMonitorValue.Subscribe(
                    deviceId,
                    monitorName,
                    (IObserver<MonitorValueUpdate<ulong>>)monitorValue,
                    new MonitorValueUpdate<ulong>(
                        description.MonitorValueUint64.Value,
                        description.MonitorValueUint64.ValueSetpoint,
                        description.MonitorValueUint64.MinValue,
                        description.MonitorValueUint64.MaxValue,
                        description.MonitorValueUint64.PhysicalMinValue,
                        description.MonitorValueUint64.PhysicalMaxValue));
            case bool:
                return _boolMonitorValue.Subscribe(
                    deviceId,
                    monitorName,
                    (IObserver<MonitorValueUpdate<bool>>)monitorValue,
                    new MonitorValueUpdate<bool>(
                        description.MonitorValueBool.Value,
                        description.MonitorValueBool.ValueSetpoint,
                        description.MonitorValueBool.MinValue,
                        description.MonitorValueBool.MaxValue,
                        description.MonitorValueBool.PhysicalMinValue,
                        description.MonitorValueBool.PhysicalMaxValue));
            case string:
                return _stringMonitorValue.Subscribe(
                    deviceId,
                    monitorName,
                    (IObserver<MonitorValueUpdate<string>>)monitorValue,
                    new MonitorValueUpdate<string>(
                        description.MonitorValueString.Value,
                        description.MonitorValueString.ValueSetpoint,
                        description.MonitorValueString.MinValue,
                        description.MonitorValueString.MaxValue,
                        description.MonitorValueString.PhysicalMinValue,
                        description.MonitorValueString.PhysicalMaxValue));
            case RemoteDeviceState:
                return _devStateMonitorValue.Subscribe(
                    deviceId,
                    monitorName,
                    (IObserver<MonitorValueUpdate<RemoteDeviceState>>)monitorValue,
                    new MonitorValueUpdate<RemoteDeviceState>(
                        (RemoteDeviceState)description.MonitorValueDeviceState.Value,
                        (RemoteDeviceState)description.MonitorValueDeviceState.ValueSetpoint,
                        (RemoteDeviceState)description.MonitorValueDeviceState.MinValue,
                        (RemoteDeviceState)description.MonitorValueDeviceState.MaxValue,
                        (RemoteDeviceState)description.MonitorValueDeviceState.PhysicalMinValue,
                        (RemoteDeviceState)description.MonitorValueDeviceState.PhysicalMaxValue));
            case RemoteWarmupState:
                return _warmupStateMonitorValue.Subscribe(
                    deviceId,
                    monitorName,
                    (IObserver<MonitorValueUpdate<RemoteWarmupState>>)monitorValue,
                    new MonitorValueUpdate<RemoteWarmupState>(
                        (RemoteWarmupState)description.MonitorValueWarmupState.Value,
                        (RemoteWarmupState)description.MonitorValueWarmupState.ValueSetpoint,
                        (RemoteWarmupState)description.MonitorValueWarmupState.MinValue,
                        (RemoteWarmupState)description.MonitorValueWarmupState.MaxValue,
                        (RemoteWarmupState)description.MonitorValueWarmupState.PhysicalMinValue,
                        (RemoteWarmupState)description.MonitorValueWarmupState.PhysicalMaxValue));
            default:
                throw new NotImplementedException();
        }
    }

    private void GrpcChannel_MonitorValueUpdate(object? sender, Primitives.MonitorValueUpdateEventArgs e)
    {
        var monVal = e.MonitorValueDescription;

        switch (monVal.DataCase)
        {
            case MonitorValueDescription.DataOneofCase.None:
                break;
            case MonitorValueDescription.DataOneofCase.MonitorValueDouble:
                _doubleMonitorValue.Update(
                    monVal.DeviceId,
                    monVal.MonitorName,
                    new MonitorValueUpdate<double>(
                        monVal.MonitorValueDouble.Value,
                        monVal.MonitorValueDouble.ValueSetpoint,
                        monVal.MonitorValueDouble.MinValue,
                        monVal.MonitorValueDouble.MaxValue,
                        monVal.MonitorValueDouble.PhysicalMinValue,
                        monVal.MonitorValueDouble.PhysicalMaxValue));
                break;
            case MonitorValueDescription.DataOneofCase.MonitorValueFloat:
                _floatMonitorValue.Update(
                    monVal.DeviceId,
                    monVal.MonitorName,
                    new MonitorValueUpdate<float>(
                        monVal.MonitorValueFloat.Value,
                        monVal.MonitorValueFloat.ValueSetpoint,
                        monVal.MonitorValueFloat.MinValue,
                        monVal.MonitorValueFloat.MaxValue,
                        monVal.MonitorValueFloat.PhysicalMinValue,
                        monVal.MonitorValueFloat.PhysicalMaxValue));
                break;
            case MonitorValueDescription.DataOneofCase.MonitorValueInt32:
                _intMonitorValue.Update(
                    monVal.DeviceId,
                    monVal.MonitorName,
                    new MonitorValueUpdate<int>(
                        monVal.MonitorValueInt32.Value,
                        monVal.MonitorValueInt32.ValueSetpoint,
                        monVal.MonitorValueInt32.MinValue,
                        monVal.MonitorValueInt32.MaxValue,
                        monVal.MonitorValueInt32.PhysicalMinValue,
                        monVal.MonitorValueInt32.PhysicalMaxValue));
                break;
            case MonitorValueDescription.DataOneofCase.MonitorValueInt64:
                _longMonitorValue.Update(
                    monVal.DeviceId,
                    monVal.MonitorName,
                    new MonitorValueUpdate<long>(
                        monVal.MonitorValueInt64.Value,
                        monVal.MonitorValueInt64.ValueSetpoint,
                        monVal.MonitorValueInt64.MinValue,
                        monVal.MonitorValueInt64.MaxValue,
                        monVal.MonitorValueInt64.PhysicalMinValue,
                        monVal.MonitorValueInt64.PhysicalMaxValue));
                break;
            case MonitorValueDescription.DataOneofCase.MonitorValueUint32:
                _uintMonitorValue.Update(
                    monVal.DeviceId,
                    monVal.MonitorName,
                    new MonitorValueUpdate<uint>(
                        monVal.MonitorValueUint32.Value,
                        monVal.MonitorValueUint32.ValueSetpoint,
                        monVal.MonitorValueUint32.MinValue,
                        monVal.MonitorValueUint32.MaxValue,
                        monVal.MonitorValueUint32.PhysicalMinValue,
                        monVal.MonitorValueUint32.PhysicalMaxValue));
                break;
            case MonitorValueDescription.DataOneofCase.MonitorValueUint64:
                _ulongMonitorValue.Update(
                    monVal.DeviceId,
                    monVal.MonitorName,
                    new MonitorValueUpdate<ulong>(
                        monVal.MonitorValueUint64.Value,
                        monVal.MonitorValueUint64.ValueSetpoint,
                        monVal.MonitorValueUint64.MinValue,
                        monVal.MonitorValueUint64.MaxValue,
                        monVal.MonitorValueUint64.PhysicalMinValue,
                        monVal.MonitorValueUint64.PhysicalMaxValue));
                break;
            case MonitorValueDescription.DataOneofCase.MonitorValueBool:
                _boolMonitorValue.Update(
                    monVal.DeviceId,
                    monVal.MonitorName,
                    new MonitorValueUpdate<bool>(
                        monVal.MonitorValueBool.Value,
                        monVal.MonitorValueBool.ValueSetpoint,
                        monVal.MonitorValueBool.MinValue,
                        monVal.MonitorValueBool.MaxValue,
                        monVal.MonitorValueBool.PhysicalMinValue,
                        monVal.MonitorValueBool.PhysicalMaxValue));
                break;
            case MonitorValueDescription.DataOneofCase.MonitorValueString:
                _stringMonitorValue.Update(
                    monVal.DeviceId,
                    monVal.MonitorName,
                    new MonitorValueUpdate<string>(
                        monVal.MonitorValueString.Value,
                        monVal.MonitorValueString.ValueSetpoint,
                        monVal.MonitorValueString.MinValue,
                        monVal.MonitorValueString.MaxValue,
                        monVal.MonitorValueString.PhysicalMinValue,
                        monVal.MonitorValueString.PhysicalMaxValue));
                break;
            case MonitorValueDescription.DataOneofCase.MonitorValueDeviceState:
                _devStateMonitorValue.Update(
                    monVal.DeviceId,
                    monVal.MonitorName,
                    new MonitorValueUpdate<RemoteDeviceState>(
                        (RemoteDeviceState)monVal.MonitorValueDeviceState.Value,
                        (RemoteDeviceState)monVal.MonitorValueDeviceState.ValueSetpoint,
                        (RemoteDeviceState)monVal.MonitorValueDeviceState.MinValue,
                        (RemoteDeviceState)monVal.MonitorValueDeviceState.MaxValue,
                        (RemoteDeviceState)monVal.MonitorValueDeviceState.PhysicalMinValue,
                        (RemoteDeviceState)monVal.MonitorValueDeviceState.PhysicalMaxValue));
                break;
            case MonitorValueDescription.DataOneofCase.MonitorValueWarmupState:
                _warmupStateMonitorValue.Update(
                    monVal.DeviceId,
                    monVal.MonitorName,
                    new MonitorValueUpdate<RemoteWarmupState>(
                        (RemoteWarmupState)monVal.MonitorValueWarmupState.Value,
                        (RemoteWarmupState)monVal.MonitorValueWarmupState.ValueSetpoint,
                        (RemoteWarmupState)monVal.MonitorValueWarmupState.MinValue,
                        (RemoteWarmupState)monVal.MonitorValueWarmupState.MaxValue,
                        (RemoteWarmupState)monVal.MonitorValueWarmupState.PhysicalMinValue,
                        (RemoteWarmupState)monVal.MonitorValueWarmupState.PhysicalMaxValue));
                break;
            default:
                break;
        }
    }
}
