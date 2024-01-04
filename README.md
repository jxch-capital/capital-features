# capital-features

股票特征库


## 开发计划

v1.0.0

* &#x2705; Yahoo API
* &#x2705; 防腐层

v1.1.0

* &#x2705; 股票池及其历史信息动态更新
* &#x2705; web管理页面：股票池管理

v1.2.0

* &#x2705; 指标策略
* 特征策略（距离计算）
  * &#x2705; DTW
  * &#x2705; 洛伦兹距离（依赖指标策略）
* 特征查询（最近邻）
  * &#x2705; KNN-DTW
  * &#x2705; KNN-洛伦兹
  * &#x2705; KNN-预测计算
  * KNN-聚合查询
    * KNN参数管理
    * KNN组合管理
    * KNN组合计算
    * 可视化
  * 可视化
    * &#x2705; 统计表
    * 显示K线片段(点击后打开)
    * &#x2705; 显示简略股价折线
    * 根据坐标系展示近邻节点
    * &#x2705; 查询参数编辑（包括指标信息）
    * websocket 显示后台处理进度
    * &#x2705; KNN 搜索条件优化
* &#x2705; 查询条件参数管理器（查询页面直接主键引用）
* &#x2705; 股票基础信息管理
* &#x2705; 股票池看板
* 股票池实时看板
* 股票池自定义指标实时看板
* 股票池自定义指标实时告警
* 支持 Docker 部署
* 利用指标数据进行神经网络训练
  * &#x2705; 指标管理
  * &#x2705; 指标组合管理（web页面）
  * &#x2705; 获取训练集测试集接口
  * &#x2705; 查询任一股票片段（预测集）
  * 神经网络构建 - python
  * 神经网络可视化 - Java加载python训练出来的模型
* 回测功能
  * &#x2705; 单只股票KNN买卖信号回测
  * &#x2705; 单只股票KNN买卖信号回测可视化
  * 使用过滤器模式销毁不良信号
    * 需要计算指标数据，然后进行过滤
    * 需要前置节点信息
* 将K近邻训练为贝叶斯模型，为避免大量重复计算

v1.3.0

* 特征查询可视化
* 特征对比可视化
* 特征统计可视化

v1.4.0

* 支持日内间隔的数据
* 支持多日间隔的数据
* 支持web页面下拉框选择
* 优化web页面体验感

v1.5.0

* 用户管理系统
* 代码重构

v2.0.0

* 对接capital-web

---

版本依赖
* TensorFlow 2.10.1 依赖 CUDA 11.2 和 cudnn 8.1.0
  * WIN下安装完CUDA后需要重启，否则JVM无法加载

---

备忘录：
* KNodeTrain里的相关特征的转变应该属于KLineFeaturesDto的职责
* 拆解KNodeParam对象（要与KNodeService行为一致）
