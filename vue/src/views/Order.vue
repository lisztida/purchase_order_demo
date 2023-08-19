<template>
  <div>

      <div>
          <el-input style="width: 200px" suffix-icon="el-icon-search" placeholder="请输入名称" v-model="username"></el-input>
          <el-button style="margin-left: 5px" type="primary" @click="load">搜索</el-button>
          <el-button style="margin-left: 5px" type="warning" @click="reset">重置</el-button>
      </div>

      <div style="margin:10px 0">
          <el-button type="primary" @click="handleAdd">新增<i class="el-icon-circle-plus-outline"></i></el-button>
          <el-button type="danger">批量删除<i class="el-icon-remove-outline"></i></el-button>
      </div>

      <el-table :data="tableData" border stripe>
          <el-table-column prop="purchaseCode" label="id" align="center">
          </el-table-column>
          <el-table-column prop="createDate" label="创建日期" align="center">
          </el-table-column>
          <el-table-column prop="createBy" label="创建人" align="center">
          </el-table-column>
          <el-table-column prop="totalPrice" label="总价" align="center">
          </el-table-column>
          <el-table-column prop="remark" label="备注" align="center">
          </el-table-column>
          <el-table-column label="操作" align="center">
              <template slot-scope="scope">
                  <el-button type="primary" size="mini" round>查看详情</el-button>
                  <el-button type="warning" size="mini" round @click="handleEdit(scope.row)">编辑 <i class="el-icon-edit"></i></el-button>
                  <el-popconfirm
                          style="margin-left: 5px"
                          confirm-button-text='确定'
                          cancel-button-text='我再想想'
                          icon="el-icon-info"
                          icon-color="red"
                          title="确定删除这位用户吗？"
                          @confirm="handleDel(scope.row.purchaseCode)"
                  >
                      <el-button type="danger" size="mini" round slot="reference">删除 <i class="el-icon-remove-outline"></i></el-button>
                  </el-popconfirm>
              </template>
          </el-table-column>
      </el-table>
      <div style="padding: 10px 0">
          <el-pagination
                  @size-change="handleSizeChange"
                  @current-change="handleCurrentChange"
                  :current-page="pageNum"
                  :page-sizes="[2, 5, 10, 20]"
                  :page-size="pageSize"
                  layout="total, sizes, prev, pager, next, jumper"
                  :total="total">
          </el-pagination>
        </div>
  </div>
</template>

<script>
export default {
    name: "User",
    data(){
        return{
            //增删改查功能
            tableData:[],
            total: 0,
            pageNum: 1,
            pageSize: 5,
            username: "",
            dialogFormVisible:false,
            form: {},
        }
    },
    created() {
        this.load()
    },
    methods:{
        load(){
            //请求分页查询数据
            this.request.get("/purchase/getAll",{
                params: {
                    pageNum: this.pageNum,
                    pageSize: this.pageSize,
                    createBy: 'admin'
                }
            }).then(res => {
                console.log(res)
                this.tableData = res.data
                this.total = res.total
            }).catch((err) => {
                console.log(err)
            })
        },
        reset(){
            this.username = ""
            this.load()
        },
        save() {
            // console.log(this.form)
            // for (var key of this.picform.keys()){
            //     console.log(key);
            // }
            this.request.post("",this.form
            ).then(res => {
                if(res){
                    this.$message.success("保存成功")
                    this.dialogFormVisible = false
                    this.load()
                }else{
                    this.$message.error("保存失败")
                }
            })
            this.request.post('/save_image/',this.picform
            ).then(res => {
                if(res){
                    this.$message.success("传图成功")
                }else{
                    this.$message.error("保存失败")
                }
            })
        },
        handleAdd() {
            this.dialogFormVisible = true
            this.form = {}
        },
        handleEdit(row){
            // this.form = Object.assign({},row)
            this.dialogFormVisible = true
        },
        handleDel(id){
            this.request.delete("/purchase/delete" + id
            ).then(res => {
                if(res){
                    this.$message.success("删除成功")
                    this.load()
                }else{
                    this.$message.error("删除失败")
                }
            })
        },
        handleSizeChange(pageSize){
            console.log(pageSize)
            this.pageSize = pageSize
            this.load()
        },
        handleCurrentChange(pageNum) {
            console.log(pageNum)
            this.pageNum = pageNum
            this.load()
        }
    }
}
</script>

<style scoped>

</style>