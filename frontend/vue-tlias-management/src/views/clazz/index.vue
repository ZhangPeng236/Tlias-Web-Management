<script setup>
import { ref, watch, onMounted } from 'vue';
import { queryAllApi as queryAllEmpApi } from '@/api/emp';
import { queryPageApi, addApi, queryInfoApi, updateApi, deleteApi } from '@/api/clazz';
import { ElMessage, ElMessageBox } from 'element-plus';

// 元数据
// 班主任列表数据
const masterIds = ref({});
// 学科列表数据
const subjects = ref([{ name: 'java', value: 1 }, { name: '前端', value: 2 }, { name: '大数据', value: 3 }, { name: 'Python', value: 4 }, { name: 'Go', value: 5 }, { name: '嵌入式', value: 6 }]);

// 搜索表单对象
const searchClazz = ref({ name: '', date: [], begin: '', end: '' })

// 侦听searchClazz的date属性
watch(() => searchClazz.value.date, (newVal, oldVal) => {
  if (newVal.length == 2) {
    searchClazz.value.begin = newVal[0];
    searchClazz.value.end = newVal[1];
  } else {
    searchClazz.value.begin = '';
    searchClazz.value.end = '';
  }
})

// 钩子函数
onMounted(() => {
  search();// 查询班级列表数据
  queryAllEmps();// 查询所有员工列表数据
})


// 查询所有员工数据
const queryAllEmps = async () => {
  const result = await queryAllEmpApi();
  if (result.code) {
    masterIds.value = result.data;
  }
}
// 查询班级列表
const search = async () => {
  const result = await queryPageApi(searchClazz.value.name, searchClazz.value.begin, searchClazz.value.end, currentPage.value, pageSize.value);
  if (result.code) {
    clazzList.value = result.data.rows;
    total.value = result.data.total;
  }
}

// 清空
const clear = () => {
  searchClazz.value = { name: '', date: [], begin: '', end: '' }
  search();
}
// 班级列表数据
const clazzList = ref([]);

// 分页
const currentPage = ref(1); // 页码
const pageSize = ref(5); // 每页展示记录数
const background = ref(true); // 背景色
const total = ref(0); // 总记录数
// 每页展示记录数变化
const handleSizeChange = (val) => {
  currentPage.value = 1;
  search();
}

// 页码变化时触发
const handleCurrentChange = (val) => {
  search();
}

// 新增/修改表单
const clazz = ref({
  name: '',
  room: '',
  beginDate: '',
  endDate: '',
  masterId: '',
  subject: '',
})

// 控制弹窗
const dialogFormVisible = ref(false);
const dialogFormTitle = ref('');

// 新增班级
const addClazz = () => {
  dialogFormVisible.value = true;
  dialogFormTitle.value = '新增班级';
  clazz.value = {
    name: '',
    room: '',
    beginDate: '',
    endDate: '',
    masterId: '',
    subject: '',
  }

  // 重置表单的校验规则-提示信息
  if (clazzFormRef.value) {
    clazzFormRef.value.resetFields();
  }
}

// 修改班级
const edit = async (id) => {
  // 重置表单的校验规则-提示信息
  if (clazzFormRef.value) {
    clazzFormRef.value.resetFields();
  }

  const result = await queryInfoApi(id);
  if (result.code) {
    dialogFormVisible.value = true;
    dialogFormTitle.value = "修改班级";
    clazz.value = result.data;
  }
}

// 保存班级
const save = async () => {
  // 表单校验
  if (!clazzFormRef.value) return;
  clazzFormRef.value.validate(async (valid) => { // valid 表示是否校验通过：true 通过 / false 不通过
    if (valid) { //通过
      let result;
      if (clazz.value.id) {//修改
        result = await updateApi(clazz.value);
      } else {//新增
        result = await addApi(clazz.value);
      }

      if (result.code) { // 成功
        ElMessage.success('保存成功');
        dialogFormVisible.value = false;
        search();
      } else { // 失败了
        ElMessage.error(result.msg);
      }
    } else {// 不通过
      ElMessage.error("表单校验不通过");
    }
  })
}

// 删除班级
const deleteById = (id) => {
  // 弹出确认框
  ElMessageBox.confirm(
    '您确认删除该班级吗？',
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

// 表单校验规则
const rules = ref({
  name: [
    { required: true, message: '请输入班级名', trigger: 'blur' },
  ],
  beginDate: [
    { required: true, message: '请输入开课时间', Trigger: 'blur' },
  ],
  endDate: [
    { required: true, message: '请选择结课时间', trigger: 'blur' }
  ],
  subject: [
    { required: true, message: '请选择学科', trigger: 'blur' },
  ]
})

// 表单引用
const clazzFormRef = ref();
</script>

<template>
  <h1>班级管理</h1>
  <!-- 搜索框 -->
  <div class="container">
    <el-form :inline="true" :model="searchClazz" class="demo-form-inline">
      <el-form-item label="班级名称">
        <el-input v-model="searchClazz.name" placeholder="请输入班级名称" />
      </el-form-item>

      <el-form-item label="结课时间">
        <el-date-picker v-model="searchClazz.date" type="daterange" range-separator="到" start-placeholder="开始日期"
          end-placeholder="结束日期" value-format="YYYY/MM/DD" />
      </el-form-item>

      <el-form-item>
        <el-button type="primary" @click="search">查询</el-button>
        <el-button type="info" @click="clear">清空</el-button>
      </el-form-item>
    </el-form>
  </div>

  <!-- 功能按钮 -->
  <div class="container">
    <el-button type="primary" @click="addClazz">+ 新增班级</el-button>
  </div>

  <!-- 数据展示表格 -->
  <div class="container">
    <el-table :data="clazzList" border style="width: 100%">
      <el-table-column type="index" label="序号" width="100" align="center" />
      <el-table-column prop="name" label="班级名称" width="180" align="center" />
      <el-table-column prop="room" label="班级教室" width="120" align="center" />
      <el-table-column prop="masterName" label="班主任" width="120" align="center" />
      <el-table-column prop="beginDate" label="开课时间" width="180" align="center" />
      <el-table-column prop="endDate" label="结课时间" width="180" align="center" />
      <el-table-column prop="status" label="状态" width="120" align="center" />
      <el-table-column prop="updateTime" label="最后操作时间" width="200" align="center" />
      <el-table-column label="操作" align="center">
        <template #default="scope">
          <el-button type="primary" size="small" @click="edit(scope.row.id)"><el-icon>
              <EditPen />
            </el-icon>编辑</el-button>
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
      :page-sizes="[2, 3, 5, 10, 20, 30, 50, 75, 100]" :background="background"
      layout="total, sizes, prev, pager, next, jumper" :total="total" @size-change="handleSizeChange"
      @current-change="handleCurrentChange" />
  </div>

  <!-- 新增/修改班级的对话框 -->
  <el-dialog v-model="dialogFormVisible" :title="dialogFormTitle" width="500">
    <el-form :model="clazz" :rules="rules" ref="clazzFormRef">
      <el-form-item label="班级名称" label-width="80px" prop="name">
        <el-input v-model="clazz.name" />
      </el-form-item>

      <el-form-item label="班级教室" label-width="80px">
        <el-input v-model="clazz.room" placeholder="请输入班级教室，2-10个字"></el-input>
      </el-form-item>

      <el-form-item label="开课日期" label-width="80px" prop="beginDate">
        <el-date-picker v-model="clazz.beginDate" type="date" style="width:100%;" placeholder="选择日期"
          formate="YYYY-MM-DD" value-format="YYYY-MM-DD"></el-date-picker>
      </el-form-item>

      <el-form-item label="结课日期" label-width="80px" prop="endDate">
        <el-date-picker v-model="clazz.endDate" type="date" style="width:100%;" placeholder="选择日期" formate="YYYY-MM-DD"
          value-format="YYYY-MM-DD"></el-date-picker>
      </el-form-item>

      <el-form-item label="班主任" label-width="80px">
        <el-select v-model="clazz.masterId" placeholder="请选择班主任" style="width:100%;">
          <el-option v-for="m in masterIds" :key="m.id" :label="m.name" :value="m.id"></el-option>
        </el-select>
      </el-form-item>

      <el-form-item label="学科" label-width="80px" prop="subject">
        <el-select v-model="clazz.subject" placeholder="请选择学科" style="width:100%;">
          <el-option v-for="s in subjects" :key="s.value" :label="s.name" :value="s.value"></el-option>
        </el-select>
      </el-form-item>
    </el-form>

    <template #footer>
      <div class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取消</el-button>
        <el-button type="primary" @click="save">确定</el-button>
      </div>
    </template>
  </el-dialog>
</template>

<style scoped>
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