# EventBridge
Like EventBus &amp; RxBus for android, And more tiny and faster.

## **使用方法**
> (0) 作为model

下载并设为Android Studio项目的model，接着在主项目的gradle配置文件中引用它。

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
        // 指定名字并绑定方法，且为sticky方式
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
        // postSticky和post是两种不同的发送方式，因此没有使用postSticky方式绑定的绑定者无法收到发送过去的数据，反之一样
        Bridge.getDefault().postSticky(MService.BRIDGE_SHOW_HELLO, "Hello,DavidChen!");
        // 给未指定名字的绑定者发送数据。指定了名字的绑定者接收不到信息，反之一样
        // sticky 方式
        Bridge.getDefault().postSticky("I'm fine.");

        // 启动服务（此服务在onCreate方法内绑定了4个方法-步骤1）
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
> (4) 跨进程通信

`Bridge不支持跨进程通信，跨进程通信需要使用 ProcessBridge。`
`当然ProcessBridge 也支持同一进程通信，只是性能比 Bridge差，因此同一进程通信时一般使用 Bridge。`
```java
    @Override
    protected void onCreate() {
        super.onCreate();
        // 绑定具有默认名字的binder
        ProcessBridge.getDefault().bind(this.getApplicationContext(), data - > {
            Log.d("Message", (String)data);
        });
    }
    // 解除EventBridge
    @Override
    public void onDestroy() {
        super.onDestroy();
        Bridge.getDefault().destroyBridge(this);
    }
    
    /* 接着在另一进程中,向绑定了默认名字的绑定者发送数据。（注意：需要使用SerialData类对传输的数据进行封装！）*/
    ProcessBridge.getDefault().postSticky(this, new SerialData("Hello,DavidChen!"));
    // ProcessBridge同时支持sticky和指定名字的方式进行通信
```
