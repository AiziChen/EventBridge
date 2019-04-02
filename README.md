# EventBridge
Like EventBus &amp; RxBus for android, But so tiny and faster.

## 一、 使用方法
> (1) 绑定方法
```java
    public void onCreate() {
        super.onCreate();
        // 绑定默认的（未指定名字的）方法
        Bridge.getDefault().bind(this, data -> {
            Log.v("Mservice-MSG", data.toString());
        });
        // 指定名字并绑定方法
        Bridge.getDefault().bind(this, "hello", data -> {
            Log.v("Mservice-MSG", data.toString());
        });
        // 绑定默认的（未指定名字的）方法，且为sticky方式
        Bridge.getDefault().bindSticky(this, data -> {
            Log.v("Mservice-MSG", data.toString());
        });
        // 绑定默认的（未指定名字的）方法，且为sticky方式
        Bridge.getDefault().bindSticky(this, "hello", data -> {
            Log.v("Mservice-MSG", data.toString());
        });
   }
```
> (2) 发送数据
```java
@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // 给指定名字的绑定者发送数据
        // sticky 方式
        // postSticky和post是两种不同的发送方式，因此没有使用post方式绑定的绑定者无法收到发送过去的数据，反之一样
        Bridge.getDefault().postSticky(MService.BRIDGE_SHOW_HELLO, "Hello,DavidChen!");
        // 给未指定名字的绑定者发送数据。指定了名字的绑定者接收不到信息，反之一样
        // sticky 方式
        Bridge.getDefault().postSticky("I'm fine.");

        // 启动服务（此服务在onStartCommand方法内绑定了两个方法）
        startService(new Intent(this, MService.class));

        // 给指定名字的绑定者发送数据
        // 非sticky 方式
        // postSticky和post是两种不同的发送方式，因此没有使用post方式绑定的绑定者无法收到发送过去的数据，反之一样
        Bridge.getDefault().post(MService.BRIDGE_SHOW_HELLO, "你好,DavidChen!");
        // 给未指定名字的绑定者发送数据。指定了名字的绑定者接收不到信息，反之一样
        // 非sticky 方式
        Bridge.getDefault().post("我很好。");
   }
```
> (3) 解除EventBridge
```java
    @Override
    public void onDestroy() {
        super.onDestroy();
        Bridge.getDefault().destroyBridge(this);
    }
```
