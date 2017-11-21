|         Name   | Default Value   | Description   |
| -------------- | --------------- | ------------- |
| smart.cmdlet.hist.max.num.records | 100000 | Maximum number of historic cmdlet records kept in SSM server. Oldest cmdlets will be deleted if exceeds the threshold. |
| smart.cmdlet.hist.max.record.lifetime | 30day | Maximum life time of historic cmdlet records kept in SSM server. Cmdlet record will be deleted from SSM server if exceeds the threshold. Valid time unit can be 'day', 'hour', 'min', 'sec'. The minimum update granularity is 5sec. |
