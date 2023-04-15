package com.permission.yiran

import androidx.fragment.app.FragmentActivity


//饿汉式单例
object PermissionX {

    private const val TAG="InvisibleFragment"

    fun request(activity:FragmentActivity,vararg permissions:String,callback:
    PermissionCallback){
        //获取FragmentManager的实例
        val fragmentManager=activity.supportFragmentManager
        //调用findFragmentByTag()方法来判断传入的Activity参数是否包含指定TAG的Fragment
        val existedFragment=fragmentManager.findFragmentByTag(TAG)
        val fragment=if(existedFragment!=null){//如果已经包含则直接使用
            existedFragment as InvisibleFragment//大到小强制转换
        }else{//否则创建一个并添加到Activity中
            val invisibleFragment=InvisibleFragment()
            //加入Tag，下次如果有新的权限要添加就不用重新new一个
            fragmentManager.beginTransaction().add(invisibleFragment,TAG).commitNow()
            invisibleFragment
        }
        fragment.requestNow(callback, *permissions)
    }
}