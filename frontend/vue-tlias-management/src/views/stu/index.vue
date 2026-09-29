<script setup>
import { ref, onMounted } from 'vue';
import { queryPageApi, addApi, queryInfoApi, updateApi, deleteApi, vioApi } from '@/api/stu';
import { queryAllApi as queryAllClazzApi } from '@/api/clazz';
import { ElMessage, ElMessageBox } from 'element-plus';

// 元数据
// 职位列表数据
const degrees = ref([{ name: '初中', value: 1 }, { name: '高中', value: 2 }, { name: '大专', value: 3 }, { name: '本科', value: 4 }, { name: '硕士', value: 5 }, { name: '博士', value: 6 }]);
// 性别列表数据
const genders = ref([{ name: '男', value: 1 }, { name: '女', value: 0 }]);
// 班级数据

// 职位列表数据
const isColleges = ref([{ name: '是', value: 1 }, { name: '否', value: 0 }]);
const clazzs = ref({});

// // 搜索表单对象
const searchStu = ref({ name: '', degree: '', clazz: '' })

// 钩子函数
onMounted(() => {
  search();// 查询学员列表数据
  queryAllClazzs();// 查询所有班级列表数据
})

// 查询所有班级数据
const queryAllClazzs = async () => {
  const result = await queryAllClazzApi();
  if (result.code) {
    clazzs.value = result.data;
  }
}
// 查询学员列表
const search = async () => {
  const result = await queryPageApi(searchStu.value.name, searchStu.value.degree, searchStu.value.clazz, currentPage.value, pageSize.value);
  if (result.code) {
    stuList.value = result.data.rows;
    total.value = result.data.total;
  }
}

// 清空
const clear = () => {
  searchStu.value = { name: '', degree: '', clazz: '' }
  search();
}

// 学员列表数据
const stuList = ref([]);

// 分页
const currentPage = ref(1); // 页码
const pageSize = ref(5); // 每页展示记录数
const background = ref(true); // 背景色
const total = ref(0); // 总记录数

// 每页展示记录数变化
const handleSizeChange = () => {
  currentPage.value = 1;
  search();
}

// 页码变化时触发
const handleCurrentChange = () => {
  search();
}

// 新增/修改表单
const stu = ref({
  name: '',
  no: '',
  gender: '',
  phone: '',
  idCard: '',
  isCollege: '',
  address: '',
  degree: '',
  graduationDate: '',
  clazzId: ''
})

// 控制弹窗
const dialogVisible = ref(false);
const dialogTitle = ref('');

//新增员工
const addStu = () => {
  dialogVisible.value = true;
  dialogTitle.value = '新增学员';
  stu.value = {
    name: '',
    no: '',
    gender: '',
    phone: '',
    idCard: '',
    isCollege: '',
    graduationDate: '',
    clazzId: ''
  }
  // 重置表单的校验规则-提示信息
  if (stuFormRef.value) {
    stuFormRef.value.resetFields();
  }
}

// 编辑
const edit = async (id) => {
  // 重置表单的校验规则-提示信息
  if (stuFormRef.value) {
    stuFormRef.value.resetFields();
  }

  const result = await queryInfoApi(id);
  if (result.code) {
    dialogVisible.value = true;
    dialogTitle.value = "修改学员";
    stu.value = result.data;
  }
}

// 表单校验规则
const rules = ref({
  name: [
    { required: true, message: '请输入姓名', Trigger: 'blur' },
  ],
  no: [
    { required: true, message: '请输入学号', trigger: 'blur' }
  ],
  gender: [
    { required: true, message: '请选择性别', trigger: 'change' }
  ],
  phone: [
    { required: true, message: '请输入手机号', trigger: 'blur' },
    { pattern: /^1[3-9]\d{9}$/, message: '请输入有效的手机号', trigger: 'blur' }
  ],
  idCard: [
    { required: true, message: '请输入身份证号', trigger: 'blur' },
    { min: 18, max: 18, message: '请输入有效的18位身份证号', trigger: 'blur' }
  ],
  isCollege: [
    { required: true, message: '请选择', trigger: 'change' }
  ],
  clazzId: [
    { required: true, message: '请选择', trigger: 'change' }
  ],
})

// 表单引用
const stuFormRef = ref();

// 保存学员
const save = async () => {
  // 表单校验
  if (!stuFormRef.value) return;
  stuFormRef.value.validate(async (valid) => { // valid 表示是否校验通过：true 通过 / false 不通过
    if (valid) { //通过
      let result;
      if (stu.value.id) {//修改
        result = await updateApi(stu.value);
      } else {//新增
        result = await addApi(stu.value);
      }

      if (result.code) { // 成功
        ElMessage.success('保存成功');
        dialogVisible.value = false;
        search();
      } else { // 失败了
        ElMessage.error(result.msg);
      }
    } else {// 不通过
      ElMessage.error("表单校验不通过");
    }
  })
}

// 复选框勾选发生变化时触发 - selection: 当前选择的记录（数组）
const handleSelectionChange = (selection) => {
  selectedIds.value = selection.map(item => item.id);
}

// 删除员工
const deleteById = (id) => {
  // 弹出确认框
  ElMessageBox.confirm(
    '您确认删除该学员吗？',
    '提示',
    {
      confirmButtonText: '确认',
      cancelButtonText: '取消',
      type: 'warning',
    }
  )
    .then(async () => { // 确认
      const result = await deleteApi(id);
      if (result.code) {
        ElMessage.success('删除成功');
        search();
      } else {
        ElMessage.error(result.msg);
      }
    })
    .catch(() => { // 取消
      ElMessage.info('您已取消删除');
    })
}

// 记录勾选的员工的id
const selectedIds = ref([]);

// 批量删除
const deleteByIds = () => {
  // 弹出确认框
  ElMessageBox.confirm(
    '您确认删除该员工吗？',
    '提示',
    {
      confirmButtonText: '确认',
      cancelButtonText: '取消',
      type: 'warning',
    }
  )
    .then(async () => { // 确认
      if (selectedIds.value && selectedIds.value.length > 0) {
        const result = await deleteApi(selectedIds.value);
        if (result.code) {
          ElMessage.success('删除成功');
          search();
        } else {
          ElMessage.error(result.msg);
        }
      } else {
        ElMessage.info('您没有选择任何要删除的数据');
      }
    })
    .catch(() => { // 取消
      ElMessage.info('您已取消删除');
    })
}

// 控制违规弹窗
const vioDialogVisible = ref(false);

// 违规表单
const vio = ref({ id: '', score: '' });

// 违规弹窗
const violation = (id) => {
  vioDialogVisible.value = true;
  vio.value = {
    id: id,
    score: ''
  }
  // 重置表单的校验规则-提示信息
  if (vioFormRef.value) {
    vioFormRef.value.resetFields();
  }
}

// 表单引用
const vioFormRef = ref();

// 表单校验规则
const vioRules = ref({
  score: [
    { required: true, message: '请输入违规扣分', trigger: 'blur' },
    { pattern: /^[1-9]{1}$/, message: '请输入有效的违规扣分(0-9)', trigger: 'blur' }
  ]
})

// 保存学员
const vioSave = async () => {
  // 表单校验
  if (!vioFormRef.value) return;
  vioFormRef.value.validate(async (valid) => { // valid 表示是否校验通过：true 通过 / false 不通过
    if (valid) { //通过
      let result = await vioApi(vio.value.id, vio.value.score);
      if (result.code) { // 成功
        ElMessage.success('保存成功');
        vioDialogVisible.value = false;
        search();
      } else { // 失败了
        ElMessage.error(result.msg);
      }
    } else {// 不通过
      ElMessage.error("表单校验不通过");
    }
  })
}
</script>

<template>
  <h1>学员管理</h1>
  <!-- 搜索框 -->
  <div class="container">
    <el-form :inline="true" :model="searchStu" class="demo-form-inline">
      <el-form-item label="姓名">
        <el-input v-model="searchStu.name" placeholder="请输入学员姓名" />
      </el-form-item>

      <el-form-item label="最高学历">
        <el-select v-model="searchStu.degree" placeholder="请选择" style="width:120px">
          <el-option v-for="d in degrees" :key="d.value" :label="d.name" :value="d.value"></el-option>
        </el-select>
      </el-form-item>

      <el-form-item label="所属班级">
        <el-select v-model="searchStu.clazz" placeholder="请选择" style="width:120px">
          <el-option v-for="c in clazzs" :key="c.id" :label="c.name" :value="c.id" />
        </el-select>
      </el-form-item>

      <el-form-item>
        <el-button type="primary" @click="search">查询</el-button>
        <el-button type="info" @click="clear">清空</el-button>
      </el-form-item>
    </el-form>
  </div>

  <!-- 功能按钮 -->
  <div class="container">
    <el-button type="primary" @click="addStu">+ 新增学员</el-button>
    <el-button type="danger" @click="deleteByIds">- 批量删除</el-button>
  </div>

  <!-- 数据展示表格 -->
  <div class="container">
    <el-table :data="stuList" border style="width: 100%" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column prop="name" label="姓名" width="100" align="center" />
      <el-table-column prop="no" label="学号" width="120" align="center" />
      <el-table-column prop="clazzName" label="班级" width="180" align="center" />
      <el-table-column label="性别" width="100" align="center">
        <template #default="scope">
          {{ scope.row.gender == 1 ? '男' : '女' }}
        </template>
      </el-table-column>
      <el-table-column prop="phone" label="手机号" width="120" align="center" />
      <el-table-column label="最高学历" width="120" align="center">
        <template #default="scope">
          <span v-if="scope.row.degree == 1">初中</span>
          <span v-else-if="scope.row.degree == 2">高中</span>
          <span v-else-if="scope.row.degree == 3">大专</span>
          <span v-else-if="scope.row.degree == 4">本科</span>
          <span v-else-if="scope.row.degree == 5">硕士</span>
          <span v-else-if="scope.row.degree == 6">博士</span>
          <span v-else>其他</span>
        </template>
      </el-table-column>
      <el-table-column prop="violationCount" label="违纪次数" width="100" align="center" />
      <el-table-column prop="violationScore" label="违纪扣分" width="100" align="center" />
      <el-table-column prop="updateTime" label="最后操作时间" width="200" align="center" />
      <el-table-column label="操作" align="center">
        <template #default="scope">
          <el-button type="primary" size="small" @click="edit(scope.row.id)"><el-icon>
              <EditPen />
            </el-icon>编辑</el-button>
          <el-button type="warning" size="small" @click="violation(scope.row.id)"><el-icon>
              <EditPen />
            </el-icon>违纪</el-button>
          <el-button type="danger" size="small" @click="deleteById(scope.row.id)"><el-icon>
              <Delete />
            </el-icon>删除</el-button>
        </template>
      </el-table-column>
    </el-table>
  </div>

  <!-- 分页条 -->
  <div class="container">
    <el-pagination v-model:current-page="currentPage" v-model:page-size="pageSize"
      :page-sizes="[5, 10, 20, 30, 50, 75, 100]" :background="background"
      layout="total, sizes, prev, pager, next, jumper" :total="total" @size-change="handleSizeChange"
      @current-change="handleCurrentChange" />
  </div>

  <!-- 新增学员/修改学员的对话框 -->
  <el-dialog v-model="dialogVisible" :title="dialogTitle">
    <el-form :model="stu" :rules="rules" ref="stuFormRef" label-width="80px">
      <!-- 基本信息 -->
      <!-- 第一行 -->
      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="姓名" prop="name">
            <el-input v-model="stu.name" placeholder="请输入学员姓名"></el-input>
          </el-form-item>
        </el-col>

        <el-col :span="12">
          <el-form-item label="学号" prop="no">
            <el-input v-model="stu.no" placeholder="请输入员工学号"></el-input>
          </el-form-item>
        </el-col>
      </el-row>

      <!-- 第二行 -->
      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="性别" prop="gender">
            <el-select v-model="stu.gender" placeholder="请选择性别" style="width: 100%;">
              <el-option v-for="g in genders" :key="g.value" :label="g.name" :value="g.value"></el-option>
            </el-select>
          </el-form-item>
        </el-col>

        <el-col :span="12">
          <el-form-item label="手机号" prop="phone">
            <el-input v-model="stu.phone" placeholder="请输入手机号"></el-input>
          </el-form-item>
        </el-col>
      </el-row>

      <!-- 第三行 -->
      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="身份证号" prop="idCard">
            <el-input v-model="stu.idCard" placeholder="请输入身份证号"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="是否院校" prop="isCollege">
            <el-select v-model="stu.isCollege" placeholder="请选择" style="width: 100%;">
              <el-option v-for="i in isColleges" :key="i.value" :label="i.name" :value="i.value"></el-option>
            </el-select>
          </el-form-item>
        </el-col>
      </el-row>

      <!-- 第四行 -->
      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="联系地址">
            <el-input v-model="stu.address" placeholder="请输入联系地址"></el-input>
          </el-form-item>
        </el-col>

        <el-col :span="12">
          <el-form-item label="最高学历">
            <el-select v-model="stu.degree" placeholder="请选择" style="width:100%;">
              <el-option v-for="d in degrees" :key="d.value" :label="d.name" :value="d.value"></el-option>
            </el-select>
          </el-form-item>
        </el-col>
      </el-row>

      <!-- 第五行 -->
      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="毕业时间">
            <el-date-picker v-model="stu.graduationDate" type="date" style="width:100%;" placeholder="请选择选择日期"
              formate="YYYY-MM-DD" value-format="YYYY-MM-DD"></el-date-picker>
          </el-form-item>
        </el-col>

        <el-col :span="12">
          <el-form-item label="所属班级" prop="clazzId">
            <el-select v-model="stu.clazzId" placeholder="请选择" style="width:100%;">
              <el-option v-for="c in clazzs" :key="c.id" :label="c.name" :value="c.id"></el-option>
            </el-select>
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>

    <!-- 底部按钮 -->
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="save">保存</el-button>
      </span>
    </template>
  </el-dialog>

  <!-- 违规的对话框 -->
  <el-dialog v-model="vioDialogVisible" title="提示">
    请输入违规扣分
    <el-form :model="vio" :rules="vioRules" ref="vioFormRef">
      <el-form-item prop="score">
        <el-input v-model="vio.score" placeholder="请输入违规扣分(0-9)"></el-input>
      </el-form-item>
    </el-form>

    <!-- 底部按钮 -->
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="vioDialogVisible = false">取消</el-button>
        <el-button type="primary" @click="vioSave">确认</el-button>
      </span>
    </template>
  </el-dialog>
</template>

<style scope>
.container {
  margin: 15px 0px;
}

.avatar {
  height: 40px;
}

.avatar-uploader .avatar {
  width: 78px;
  height: 78px;
  display: block;
}

.avatar-uploader .el-upload {
  border: 1px dashed var(--el-border-color);
  border-radius: 6px;
  cursor: point;
  position: relative;
  overflow: hidden;
  transition: var(--el-transition-duration-fast);
}

.avatar-uploader .el-upload:hover {
  border-color: var(--el-color-primary);
}

.el-icon.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 78px;
  height: 78px;
  text-align: center;
  border-radius: 10px;
  /* 添加灰色的虚线边框 */
  border: 1px dashed var(--el-border-color);
}
</style>