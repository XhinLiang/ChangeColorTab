# ChangeColorTab

####有的时候再好的想法也抵不过领导的一句话.

![Alt text](./sample.gif)


给老师做一个项目,给他推荐使用Material Design,结果老师不同意,坚持要仿微信的风格,那没办法了,既然是老师拍板,不做也得做.
很庆幸有Hongyang的博文和代码,花了一个下午的时间大概把这个自定义View写好,事实上这也是我第一次写自定义View.
基本功能已经大概成型了,后期如果有什么Bug或者好的想法会往里面加,现在使用起来已经足够简洁足够方便了.

**这个库有几乎一半的代码出自Hongyang之手**
非常感谢鸿洋 ,有很多的东西都是看他的博客学会的.
这里贴出来原博客地址:
http://blog.csdn.net/lmj623565791/article/details/41087219



ChangeColorTab is a simple tablayout extends LinearLayout , Host For ChangeColorItem.
ChangeColorItem is a custom View which can change it's color when you scroll ViewPager.

## 使用

####Gradle添加依赖

     compile 'com.xdu.xhin:library:0.1.0'

####在layout中加入ChangeColorTab和ChangeColorItem

    <android.support.v4.view.ViewPager
        android:id="@+id/id_viewpager"
        android:layout_width="match_parent"
        android:layout_height="0dp"
        android:layout_weight="1" />

    <com.xdu.xhin.library.view.ChangeColorTab
        android:id="@+id/change_color_tab"
        android:layout_width="match_parent"
        android:layout_height="60dp">

        <com.xdu.xhin.library.view.ChangeColorItem
            android:layout_width="0dp"
            android:layout_height="fill_parent"
            android:layout_weight="1"
            android:padding="5dp"
            tab:item_color="#ff45c01a"
            tab:item_icon="@drawable/ic_menu_start_conversation"
            tab:item_text="tab1"
            tab:item_text_size="12sp" />

        <com.xdu.xhin.library.view.ChangeColorItem
            android:id="@+id/id_indicator_two"
            android:layout_width="0dp"
            android:layout_height="fill_parent"
            android:layout_weight="1"
            android:padding="5dp"
            tab:item_color="@color/colorAccent"
            tab:item_icon="@drawable/ic_menu_friendslist"
            tab:item_text="tab2"
            tab:item_text_size="12sp" />

        <com.xdu.xhin.library.view.ChangeColorItem
            android:layout_width="0dp"
            android:layout_height="fill_parent"
            android:layout_weight="1"
            android:padding="5dp"
            tab:item_color="@color/colorPrimary"
            tab:item_icon="@drawable/ic_menu_emoticons"
            tab:item_text="tab3"
            tab:item_text_size="12sp" />

        <com.xdu.xhin.library.view.ChangeColorItem
            android:layout_width="0dp"
            android:layout_height="fill_parent"
            android:layout_weight="1"
            android:padding="5dp"
            tab:item_color="#ff45c01a"
            tab:item_icon="@drawable/ic_menu_allfriends"
            tab:item_text="tab4"
            tab:item_text_size="12sp" />
    </com.xdu.xhin.library.view.ChangeColorTab>

####绑定ViewPager
   

    mViewPager = (ViewPager) findViewById(R.id.id_viewpager);
    changeColorTab = (ChangeColorTab) findViewById(R.id.change_color_tab);
    changeColorTab.setViewpager(mViewPager);

## 反馈与建议
- 邮箱：<xhinliang@gmail.com>

