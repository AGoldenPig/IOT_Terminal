1. readDeviceCnt
- 功能：读设备数量
- 功能码：0X03x
- 功能描述：读单寄存器0xFFFF
- 格式：[从机地址码][0x03][FFFF][0001][CRC校验]
- 回复：[从机地址码][0x03][寄存器数据(4byte)][CRC校验]
- 回复长度：8byte
2. readDeviceInfo
- 功能：读设备信息
- 功能码：0x03
- 功能描述：读设备地址空间0x0000-0xFFFE对应设备的信息
- 格式：[从机地址码][0x03][设备空间起始地址(2byte)][设备数(2byte)][CRC校验]
- 回复：[从机地址码][0x03][[设备ID(4byte)][设备类型ID(4byte)][设备LoRa地址(2byte)][设备启用状态(1byte)][设备连接状态(1byte)]][CRC校验]
- 回复长度：(4+12*设备数)byte
3. readDeviceDataCnt
- 功能：读设备数据数量
- 功能码：0x04
- 功能描述：读设备地址空间0x0000-0xFFFE对应设备的数据条数
- 格式：[从机地址码][0x04][FFFF][0001][CRC校验]
- 回复：[从机地址码][0x04][寄存器数据(4byte)][CRC校验]
4. readDeviceData
- 功能：读设备数据
- 功能码：0x04
- 功能描述：读设备地址空间0x0000-0xFFFE对应设备的数据条数
- 格式：[从机地址码][0x04][0000][数据量][CRC校验]
- 回复：[从机地址码][0x04][[传感器ID(4byte)][数据值(4byte)][时间(8byte)]……][CRC校验]