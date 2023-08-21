<template>
    <div>
        <!-- <div>
            <el-input style="width: 200px" suffix-icon="el-icon-search" placeholder="请输入名称" v-model="username"></el-input>
            <el-button style="margin-left: 5px" type="primary" @click="load">搜索</el-button>
            <el-button style="margin-left: 5px" type="warning" @click="reset">重置</el-button>
        </div> -->

        <div style="margin:10px 0">
            <el-button type="primary" @click="handleAdd">新增<i class="el-icon-circle-plus-outline"></i></el-button>
            <!-- <el-button type="danger">批量删除<i class="el-icon-remove-outline"></i></el-button> -->
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
                <el-descriptions title="供应商">
                    <el-descriptions-item label="供应商">{{ this.supplier }}</el-descriptions-item>
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
                            <el-select v-model="supplierCode" placeholder="请选择供应商"
                                       @change = 'handleSupplierChange'>
                                <el-option label="九七果蔬农贸有限公司1" value="SP202105"></el-option>
                                <el-option label="九七果蔬农贸有限公司2" value="SP202106"></el-option>
                                <el-option label="九七果蔬农贸有限公司3" value="SP202107"></el-option>
                            </el-select>
                        </el-form-item>
                    </el-form>
                    <h3>采购明细</h3>
                    <el-button :disabled="supplierCode === ''"
                               type="primary" 
                               icon="el-icon-plus" 
                               @click="addItem">添加商品</el-button>
                    <el-table :data="formData.purchaseOrderItems" stripe border>
                        <el-table-column prop="product.supplier.supplierName" label="供应商" align="center" width="200px">
                            <template slot-scope="scope">
                                <el-input v-model="scope.row.product.supplier.supplierName" :disabled="true"></el-input>
                            </template>
                        </el-table-column>
                        <el-table-column prop="productCode" label="产品编码" align="center">
                            <template slot-scope="scope">
                                <el-input v-model="scope.row.productCode" :disabled="true"></el-input>
                            </template>
                        </el-table-column>
                        <el-table-column prop="product.productCategory" label="产品分类" align="center">
                            <template slot-scope="scope">
                                <el-input v-model="scope.row.product.productCategory" :disabled="true"></el-input>
                            </template>
                        </el-table-column>
                        <el-table-column prop="product.productName" label="产品名称" align="center">
                            <template slot-scope="scope">
                                <el-select v-model="scope.row.product.productName" placeholder="请选择商品"
                                           @change="handleProductChange(scope.$index)">
                                    <el-option
                                        v-for="product in products"
                                        :key="product.productCode"
                                        :label="product.productName"
                                        :value="product.productName">
                                    </el-option>
                                </el-select>
                            </template>
                        </el-table-column>
                        <el-table-column prop="product.specType" label="规格型号" align="center">
                            <template slot-scope="scope">
                                <el-input v-model="scope.row.product.specType" :disabled="true"></el-input>
                            </template>
                        </el-table-column>
                        <el-table-column prop="product.unit" label="单位" align="center" width="80px">
                            <template slot-scope="scope">
                                <el-input v-model="scope.row.product.unit" :disabled="true"></el-input>
                            </template>
                        </el-table-column>
                        <el-table-column prop="product.price" label="采购单价" align="center" width="80px">
                            <template slot-scope="scope">
                                <el-input v-model="scope.row.product.price" :disabled="true"></el-input>
                            </template>
                        </el-table-column>
                        <el-table-column prop="nums" label="采购数量" align="center" width="180px">
                            <template slot-scope="scope">
                                <el-input-number v-model="scope.row.nums" 
                                                 :min="1"
                                                 :max="20"
                                                 controls-position="right"
                                                 size="mini"
                                                 @change="handleNumsChange(scope.$index)"
                                                 align="center"></el-input-number>
                            </template>
                        </el-table-column>
                        <el-table-column prop="totalPrice" label="小计" align="center">
                            <template slot-scope="scope">
                                <el-input v-model="scope.row.totalPrice" :disabled="true"></el-input>
                            </template>
                        </el-table-column>
                        <el-table-column label="移除" align="center" width="50px">
                            <template slot-scope="scope">
                            <el-button type="danger" icon="el-icon-delete" circle size="mini" @click="removeItem(scope.$index)"></el-button>
                            </template>
                        </el-table-column>
                    </el-table>
                    <el-form :model="formData">
                        <h3>总金额</h3>
                        <el-input v-model="formData.totalPrice" :disabled="true"></el-input>
                        <h3>备注</h3>
                        <el-input type="textarea" :rows="3" v-model="formData.remark"></el-input>
                    </el-form>
                </div>
                <span slot="footer" class="dialog-footer">
                    <el-button @click="dialogFormVisible = false">取 消</el-button>
                    <el-button :disabled="formData.totalPrice <= 0"
                               type="primary" 
                               @click="save">确 定</el-button>
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
            //表明表单是新建还是修改
            insertFlag: true,
            //订单总览
            tableData:[],
            //查看详情
            detailData:[],
            productData:[],
            //分页查询数据
            total: 0,
            pageNum: 1,
            pageSize: 2,
            //订单新增/修改用参数
            supplier:"",
            supplierCode:"",
            nowProductName:"",
            nowProduct:{},
            products:[],
            //订单新增/修改/查看页面
            detailVisible:false,
            dialogFormVisible:false,
            //表单信息
            newPurchaseCode: "",
            newCreateDate: "",
            newCreateBy: "admin",
            //提交表单
            formData: {},
            //提交表单组成部分
            formPurchaseOrderItem: {}
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
                this.total = res.totalNum
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
                this.formData = resList[0]
                // console.log(this.detailData)
                // console.log(this.productData)
                this.supplierCode = this.formData.purchaseOrderItems[0].product.supplier.supplierCode
                this.supplier = this.formData.purchaseOrderItems[0].product.supplier.supplierName
            }).catch(err => {
                console.log(err)
            })
        },
        getProductsBySup(supplierCode){
            //选取企业后确定可以选择的产品
            this.request.get("/product/getBySup",{
                params: {
                    supplierCode: supplierCode,
                }
            }).then(res => {
                // console.log(res)
                this.products = res
                this.supplier = res[0].supplier.supplierName;
                this.formData.purchaseOrderItems = []
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
                []
            }
        },
        addItem(){
            //添加一个purchaseOredrItem
            this.formPurchaseOrderItem = 
            {
                purchaseCode: this.newPurchaseCode,
                supplierCode: this.products[0].supplier.supplierCode,
                productCode: '',
                nums: 1,
                totalPrice: 0.00,
                product: {
                    productName: '',
                    supplier:{
                        supplierName: this.products[0].supplier.supplierName,
                    },
                    productCategory: '',
                    productName: '',
                    specType: '',
                    unit: '',
                    price: '',
                }
            }
            this.formData.purchaseOrderItems.push(this.formPurchaseOrderItem)
            // console.log(this.formData)
        },
        removeItem(index){
            //移除一个purchaseOrderItem
            this.formData.purchaseOrderItems.splice(index,1)
            console.log(this.formData)
        },
        reset(){
            this.username=''
            this.load()
        },
        save() {
            //将formData表单作为body post传到后端
            // console.log(this.formData)
            if(this.insertFlag){
                this.request.post("/purchase/insert",this.formData
                    ).then(res => {
                        if(res){
                            this.$message.success("保存成功")
                            this.dialogFormVisible = false
                            this.load()
                        }else{
                            this.$message.error("保存失败")
                        }
                    })
            }else{
                this.request.post("/purchase/update",this.formData
                    ).then(res => {
                        if(res){
                            this.$message.success("保存成功")
                            this.dialogFormVisible = false
                            this.load()
                        }else{
                            this.$message.error("保存失败")
                        }
                    })
            }
            
        },
        handleAdd() {
            //新增订单，将状态改为新增
            this.insertFlag = true
            this.initForm()
            this.supplierCode = ''
            this.setNewPurchaseCode()
            this.formData.purchaseCode=this.newPurchaseCode
            this.formData.createDate=this.newCreateDate
            this.dialogFormVisible = true
        },
        handleDetail(id){
            //获取订单详情
            this.loadDetail(id)
            this.detailVisible = true
        },
        handleEdit(id){
            //修改订单信息，将状态变为修改
            // this.form = Object.assign({},row)
            this.insertFlag = false
            //获取订单详情 
            this.loadDetail(id)
            this.dialogFormVisible = true
        },
        handleDel(id){
            this.request.delete("/purchase/delete",{
                params:{
                    purchaseCode: id
                }
            }).then(res => {
                if(res){
                    this.$message.success("删除成功")
                    this.load()
                }else{
                    this.$message.error("删除失败")
                }
            })
        },
        //分页查询相关
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
        //新增订单时的参数改变相关
        handleSupplierChange(supplierCode){
            //选择供应商
            this.getProductsBySup(supplierCode)
        },
        handleProductChange(index){
            //找到所选择的产品
            this.nowProductName = this.formData.purchaseOrderItems[index].product.productName
            // console.log(this.nowProductName)
            // console.log(this.products)
            //遍历产品列表
            this.products.forEach((product) => {
                if(product.productName === this.nowProductName){
                    this.nowProduct = product;
                    // console.log(this.nowProduct)
                }
            })
            this.formData.purchaseOrderItems[index].product = this.nowProduct
            this.formData.purchaseOrderItems[index].productCode = this.nowProduct.productCode
        },
        handleNumsChange(index){
            const newOneTotalPrice = this.formData.purchaseOrderItems[index].product.price * this.formData.purchaseOrderItems[index].nums
            let newTotalPrice = 0.00
            this.formData.purchaseOrderItems[index].totalPrice = newOneTotalPrice
            this.formData.purchaseOrderItems.forEach((item) => {
                newTotalPrice += item.totalPrice;
            })
            // console.log(newTotalPrice)
            this.formData.totalPrice = newTotalPrice
        }
    }
}
</script>

<style scoped>

</style>