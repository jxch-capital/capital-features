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
* &#x2705; 指标数据归一化
* 特征策略（距离计算）
  * &#x2705; DTW
  * &#x2705; 洛伦兹距离（依赖指标策略）
* 特征查询（最近邻）
  * &#x2705; KNN-DTW
  * &#x2705; KNN-洛伦兹
  * &#x2705; KNN-预测计算
  * KNN-聚合查询
    * &#x2705; KNN策略功能
    * &#x2705; 多KNN聚合更新，保存信号回测结果
    * &#x2705; KNN增量更新
    * &#x2705; 可视化KNN更新结果
    * &#x2705; 多KNN信号叠加规则
    * &#x2705; 多KNN动态可视化
    * 可视化信号叠加关系，堆叠图
    * 设置止损止盈策略及回测的可视化（模型信号角度和统计角度都要有）
    * 为统计器加入止损止盈策略
    * 为统计器加入资金回测
    * 资金管理策略
    * 为统计器加入资金管理策略
    * web回测界面自定义风险管理止损止盈资金管理的策略
  * 可视化
    * &#x2705; 统计表
    * 显示K线片段(点击后打开)
    * &#x2705; 显示简略股价折线
    * 根据坐标系展示近邻节点
    * &#x2705; 查询参数编辑（包括指标信息）
    * websocket 显示后台处理进度
    * &#x2705; KNN 搜索条件优化
    * 股票池可以多选
  * 使用向量数据库
* &#x2705; 查询条件参数管理器（查询页面直接主键引用）
* &#x2705; 股票基础信息管理
* &#x2705; 股票池看板
  * 聚合看板
* 股票池实时看板
* 股票池自定义指标实时看板
* 股票池自定义指标实时告警
* &#x2705; 多种距离算法支持
  * &#x2705; 多维取平均
  * &#x2705; 多维展开为一维
* &#x2705; 指标归一化距离算法支持
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
    * &#x2705; 需要计算指标数据，然后进行过滤
    * &#x2705; 异向信号抑制功能迁移到过滤器
    * 重复过滤功能，API
  * 资金回测（模拟交易）
  * GPU加速
  * 动态计算片段近邻的信号
  * 股票池可以多选
  * 多距离算法计算，聚合展示，可动态调整
  * 胜率和股票池里的标的相似性有关，和指标的合理性也有关（趋势和波动）
    * 进行针对性的整理
    * 个股及股票池的特征可视化对比，以方便选取
* 将K近邻训练为贝叶斯模型，为避免大量重复计算
* 大语言对话模型
  * &#x2705; Gemini Pro
  * &#x2705; WEB
  * &#x2705; Markdown
  * &#x2705; 流式读取
  * &#x2705; 角色管理
  * &#x2705; 多知识接口
  * 多角色对话
  * 前端手动选择要加哪些历史对话
  * 数据库存放会话信息
  * 对接python知识库
    * &#x2705; 查询
* 订阅者模式向前端发送数据查询进度
* 自动创建数据库表
* 核函数分类器
  * &#x2705; SVM+高斯核
  * 可视化
  * &#x2705; 模型保存和加载
    * 可视化
* 分类器设置以及训练更新管理功能
  * &#x2705; 分类器配置功能
  * &#x2705; 分类器模型配置功能
  * &#x2705; 更新训练模型并保存功能及页面
  * &#x2705; 信号回测页面
  * 支持KNN距离算法配置
* 前端重构
  * &#x2705; 菜单模块化
* 邮件服务
  * 简报
  * 告警
    * 指标组合信号
    * KNN信号
  * 实时告警
* 交易笔记(5m)
  * &#x2705; 标记跟踪笔记
  * &#x2705; type和key使用枚举类型下拉框
  * 笔记备份：本地，GitHub，anki
  * &#x2705; 基于股票池查询（因为yahoo接口仅能够查询最近60日的）
  * &#x2705; 股票池增量更新
  * 股票池备份

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

v1.6.0

* 大语言模型 (暂时使用python)
  * DJL 加载 Hugging Face transformers 的词向量模型
  * Gemini Pro api 进行推理
  * 使用 Milvus 作为向量数据库

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
