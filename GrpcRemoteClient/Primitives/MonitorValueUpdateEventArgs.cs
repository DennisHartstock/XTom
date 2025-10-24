using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

using XTomRemoteClient;

namespace GrpcRemoteClient.Primitives;
internal class MonitorValueUpdateEventArgs : EventArgs
{
    public MonitorValueDescription MonitorValueDescription { get; }

    public MonitorValueUpdateEventArgs(MonitorValueDescription monitorValueDescription)
    {
        MonitorValueDescription = monitorValueDescription;
    }
}
