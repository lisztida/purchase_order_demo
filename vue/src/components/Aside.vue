<template>
  <el-menu :default-openeds="['1', '3']" style="height: 100%; overflow-x: hidden"
           background-color="rgb(48,65,86)"
           text-color="#fff"
           active-text-color="#ffd04b"
           router>
    <div style="height: 60px; line-height: 60px; text-align: center">
      <img src="../assets/logo.png" alt="" style="width: 20px; position: relative; top: 5px; margin-right: 5px">
      <b style="color:white">后台管理系统</b>
    </div>

    <el-menu-item index="/">
        <template slot="title">
            <i class="el-icon-house"></i>
            <span slot="title">主页</span>
        </template>
    </el-menu-item>
    <el-submenu index="2">
      <template slot="title"><i class="el-icon-menu"></i>订单管理</template>
      <el-menu-item-group>
        <el-menu-item index="/order">订单管理</el-menu-item>
      </el-menu-item-group>
    </el-submenu>
  </el-menu>
</template>

<script>
export default {
  name: "Aside",
}
</script>

<style>

</style>