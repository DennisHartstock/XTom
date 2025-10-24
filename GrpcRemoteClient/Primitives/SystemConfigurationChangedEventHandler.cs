using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

using XTomRemoteClient;

namespace GrpcRemoteClient.Primitives;
internal class SystemConfigurationChangedEventHandler : EventArgs
{
    public SystemConfigurationResponse SystemConfiguration { get; }

    public SystemConfigurationChangedEventHandler(SystemConfigurationResponse systemConfiguration)
    {
        SystemConfiguration = systemConfiguration;
    }
}
