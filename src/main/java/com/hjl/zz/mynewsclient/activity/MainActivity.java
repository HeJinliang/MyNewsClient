package com.hjl.zz.mynewsclient.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.hjl.zz.mynewsclient.R;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;

import com.hjl.zz.mynewsclient.fragment.ContentFragment;
import com.hjl.zz.mynewsclient.fragment.MenuFragment;

public class MainActivity extends SlidingFragmentActivity {
    //public MySlidingMenu slidingMenu;

    public SlidingMenu slidingMenu;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.slidingmenu_content);
        setBehindContentView(R.layout.slidingmenu_menu);

//        1. 菜单的位置 (左侧,右侧,左右)
//        2. 打开方式 (全屏幕打开,边缘打开,不可打开)
//        3. 菜单打开宽度
//        4. 分割线的样式,宽度

        slidingMenu = getSlidingMenu();
        //slidingMenu = new SlidingMenu(this);

        //菜单位置，左侧
        slidingMenu.setMode(SlidingMenu.LEFT);
        //打开方式，全屏幕打开
        slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
        //菜单打开宽度
        slidingMenu.setBehindWidthRes(R.dimen.slidingmenu_menu_width);
        //分割线的样式，宽度
        slidingMenu.setShadowDrawable(R.drawable.shadow);
        slidingMenu.setShadowWidthRes(R.dimen.slidingmenu_shadow_width);


        replaceContent(new ContentFragment());

        getSupportFragmentManager().beginTransaction().replace(R.id.fl_sliding_menu,new MenuFragment(),"menu").commit();
    }

    /**
     * 替换内容
     */
    public void replaceContent(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.fl_sliding_content,fragment,"content").commit();
    }

    /**
     * 获取MenuFragment
     * @return
     */
    public MenuFragment getMenuFragment(){
        MenuFragment menuFragment = (MenuFragment) getSupportFragmentManager().findFragmentByTag("menu");
        return menuFragment;
    }

    /**
     * 获取MenuFragment
     * @return
     */
    public ContentFragment getContentFragment(){
        ContentFragment contentFragment = (ContentFragment) getSupportFragmentManager().findFragmentByTag("content");
        return contentFragment;
    }

}
