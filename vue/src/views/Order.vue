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
                <template slot-scope="scope">
                    <span v-if="scope.row.remark">{{ scope.row.remark }}</span>
                    <span v-else>暂无</span>
                </template>
            </el-table-column>
            <el-table-column label="操作" align="center">
                <template slot-scope="scope">
                    <el-button type="primary" size="mini" round @click="handleDetail(scope.row.purchaseCode)">查看详情</el-button>
                    <el-button type="warning" size="mini" round @click="handleEdit(scope.row.purchaseCode)">编辑 <i class="el-icon-edit"></i></el-button>
                    <el-popconfirm
                            style="margin-left: 5px"
                            confirm-button-text='确定'
                            cancel-button-text='我再想想'
                            icon="el-icon-info"
                            icon-color="red"
                            title="确定删除订单吗？"
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
        <div>
            <el-dialog title="订单详情" :visible="this.detailVisible" width="80%">
                <el-descriptions title="基础信息">
                    <el-descriptions-item label="单据编号">{{ this.detailData.purchaseCode }}</el-descriptions-item>
                    <el-descriptions-item label="创建日期">{{ this.detailData.createDate }}</el-descriptions-item>
                    <el-descriptions-item label="创建人">{{ this.detailData.createBy }}</el-descriptions-item>
                </el-descriptions>
                <h3>采购明细</h3>
                <el-table :data="productData" border stripe>
                    <el-table-column prop="product.supplier.supplierName" label="供应商" align="center" width="250px"></el-table-column>
                    <el-table-column prop="product.productCode" label="产品编码" align="center"></el-table-column>
                    <el-table-column prop="product.productCategory" label="产品分类" align="center"></el-table-column>
                    <el-table-column prop="product.productName" label="产品名称" align="center"></el-table-column>
                    <el-table-column prop="product.specType" label="规格型号" align="center"></el-table-column>
                    <el-table-column prop="product.unit" label="单位" align="center"></el-table-column>
                    <el-table-column prop="product.price" label="采购单价" align="center"></el-table-column>
                    <el-table-column prop="nums" label="采购数量" align="center"></el-table-column>
                    <el-table-column prop="totalPrice" label="小计" align="center"></el-table-column>
                </el-table>
                <el-descriptions title="总金额">
                    <el-descriptions-item label="总金额">{{ this.detailData.totalPrice }}</el-descriptions-item>
                </el-descriptions>
                <el-descriptions title="备注">
                    <el-descriptions-item label="备注">
                        {{ this.detailData.remark ? this.detailData.remark:'暂无'}}
                    </el-descriptions-item>
                </el-descriptions>
                <span slot="footer" class="dialog-footer">
                    <el-button type="primary" @click="detailVisible = false">确 定</el-button>
                </span>
            </el-dialog>
        </div>
        <div>
            <el-dialog title="新建/修改订单" :visible="this.dialogFormVisible" width="80%">
                <div>
                    <h3>基础信息</h3>
                    <el-form :model="formData" :inline="true">
                        <el-form-item label="订单编号">
                            <el-input v-model="formData.purchaseCode" :disabled="true" :placeholder="this.newPurchaseCode"></el-input>
                        </el-form-item>
                        <el-form-item label="创建日期">
                            <el-date-picker v-model="formData.createDate" type="date" :disabled="true" :placeholder="this.newCreateDate"></el-date-picker>
                        </el-form-item>
                        <el-form-item label="创建人">
                            <el-input v-model="formData.createBy" :disabled="true" placeholder="admin"></el-input>
                        </el-form-item>
                    </el-form>
                    <h3>供应商</h3>
                    <el-form :model="formData">
                        <el-form-item>
                            <el-select v-model="formData.purchaseOrderItems.supplierCode" placeholder="请选择供应商"
                                       @change = 'handleSupplierChange'>
                                <el-option label="九七果蔬农贸有限公司1" value="SP202105"></el-option>
                                <el-option label="九七果蔬农贸有限公司2" value="SP202106"></el-option>
                                <el-option label="九七果蔬农贸有限公司3" value="SP202107"></el-option>
                            </el-select>
                        </el-form-item>
                    </el-form>
                    <h3>采购明细</h3>
                    <el-form :model="formData">
                        <el-table :data="formData.purchaseOrderItems" style="margin-top: 20px">
                            <el-table-column label="供应商" width="250px" prop="">
                                <template slot-scope="{row}">
                                    <el-form-item v-for="(item,index) in row.product" :key="index">
                                        <el-input v-model="item.supplier.supplierName" :placeholder="item.supplier.supplierName"></el-input>
                                    </el-form-item>
                                </template>
                            </el-table-column>
                        </el-table>
                    </el-form>
                </div>
                <span slot="footer" class="dialog-footer">
                    <el-button @click="dialogFormVisible = false">取 消</el-button>
                    <el-button type="primary" @click="dialogFormVisible = false">确 定</el-button>
                </span>
            </el-dialog>
        </div>
        
    </div>
</template>

<script>
export default {
    name: "User",
    data(){
        return{
            //增删改查功能
            username: '',
            //订单总览
            tableData:[],
            //查看详情
            detailData:[],
            productData:[],
            //分页查询数据
            total: 0,
            pageNum: 1,
            pageSize: 5,
            //订单新增/修改用参数
            supplier:"",
            products:[],
            //订单新增/修改/查看页面
            detailVisible:false,
            dialogFormVisible:false,
            //表单信息
            newPurchaseCode: "",
            newCreateDate: "",
            newCreateBy: "admin",
            //提交表单
            formData: {}
        }
    },
    created() {
        this.load()
        this.initForm()
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
        loadDetail(id){
            //异步查询数据，查询完成后再显示对话框
            Promise.all([
                //这是其中一个完整的查询请求，可以查询多个
                this.request.get("purchase/getPurchase",{
                    params: {
                        purchaseId: id
                    }
                })
            ]).then(resList => {
                this.detailData = resList[0]
                this.productData = resList[0].purchaseOrderItems
                // console.log(this.detailData)
                // console.log(this.productData)
                this.detailVisible = true
            }).catch(err => {
                console.log(err)
            })
        },
        getProductsBySup(supplierCode){
            this.request.get("/product/getBySup",{
                params: {
                    supplierCode: supplierCode,
                }
            }).then(res => {
                console.log(res)
                this.products = res
                this.supplier = res[0].supplier.supplierName;
            })
        },
        //获取日期和生成订单号
        setNewPurchaseCode(){
            const currentDate = new Date();
            const currentDetailDate = currentDate.toISOString().slice(0,10);
            this.newCreateDate = currentDetailDate
            const numDate = currentDetailDate.toString().replace(/-/g,"")
            const hour = currentDate.getHours();
            const minute = currentDate.getMinutes();
            const second = currentDate.getSeconds();
            const num = ((hour*3600+3600+minute*60+60+second)/10000).toString().replace(".","").padEnd(5,"0");
            this.newPurchaseCode = "CG_JQ"+numDate+num;
        },
        initForm(){
            //初始化表单
            this.formData = 
            {
                purchaseCode: this.newPurchaseCode,
                createDate: this.newCreateDate,
                createBy: "admin",
                totalPrice: 0.0,
                remark: "",
                purchaseOrderItems: 
                [
                    {
                        product:{
                            supplier:{
                                supplierName: "",
                            }
                        }
                    }
                ]
            }
        },
        reset(){
            this.username=''
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
        },
        //新增订单
        handleAdd() {
            this.initForm()
            this.setNewPurchaseCode()
            this.dialogFormVisible = true
        },
        handleDetail(id){
            this.loadDetail(id)
        },
        handleEdit(id){
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
        },
        handleSupplierChange(supplierCode){
            this.getProductsBySup(supplierCode)
        }
    }
}
</script>

<style scoped>

</style>