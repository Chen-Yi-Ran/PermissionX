package com.permission.yiran

import android.content.pm.PackageManager
import androidx.fragment.app.Fragment

typealias PermissionCallback=(Boolean, List<String>) -> Unit
class InvisibleFragment: Fragment() {
    //callback为函数类型变量，可为空
    private var callback: PermissionCallback? =null
    fun requestNow(cb:PermissionCallback,vararg permissions:String){
        callback=cb
        requestPermissions(permissions,1)
    }
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode==1){
            //使用deniedList列表来记录所有被用户拒绝的权限
            val  deniedList=ArrayList<String>()
            for((index,result) in grantResults.withIndex()){
                //如果发现某个权限未被用户授权
                if(result!=PackageManager.PERMISSION_GRANTED){
                    deniedList.add(permissions[index])
                }
            }
            //标识是否所有申请权限均已被授权，如果为空说明都已授权
            val allGranted=deniedList.isEmpty()
            //let函数用于判空，it代表callback对象
            callback?.let {
                it(allGranted,deniedList)
            }
        }
    }
}