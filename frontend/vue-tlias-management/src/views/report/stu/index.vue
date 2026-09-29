<script setup>
import { ref, onMounted, computed } from 'vue';
import { studentCountDataApi, studentDegreeDataApi } from '@/api/report';

const studentCountClazzList = ref([]);
const studentCountDataList = ref([]);
const studentDegree = ref();

// 钩子函数
onMounted(() => {
  search();
})

//查询
const search = async () => {
  let result;
  result = await studentCountDataApi();
  if (result.code) {
    studentCountClazzList.value = result.data.clazzList;
    studentCountDataList.value = result.data.dataList;
  }

  result = await studentDegreeDataApi();
  if (result.code) {
    studentDegree.value = result.data;
  }
}

const optionStudentCount = computed(() => {
  return {
    title: {
      text: '班级人数统计'
    },
    xAxis: {
      type: 'category',
      data: studentCountClazzList.value
    },
    yAxis: {
      type: 'value'
    },
    series: [
      {
        data: studentCountDataList.value,
        type: 'bar'
      }
    ]
  }
});

const optionStudentDegree = computed(() => {
  return {
    title: {
      text: '学员学历统计'
    },
    tooltip: {
      trigger: 'item'
    },
    legend: {
      top: '5%',
      left: 'center'
    },
    series: [
      {
        name: 'Access From',
        type: 'pie',
        radius: ['40%', '70%'],
        avoidLabelOverlap: false,
        itemStyle: {
          borderRadius: 10,
          borderColor: '#fff',
          borderWidth: 2
        },
        label: {
          show: false,
          position: 'center'
        },
        emphasis: {
          label: {
            show: true,
            fontSize: 40,
            fontWeight: 'bold'
          }
        },
        labelLine: {
          show: false
        },
        data: studentDegree.value
      }
    ]
  }
});
</script>

<template>
  <el-row :gutter="20">
    <el-col :span="12">
      <v-chart class="chart" :option="optionStudentCount" />
    </el-col>
    <el-col :span="12">
      <v-chart class="chart" :option="optionStudentDegree" />
    </el-col>
  </el-row>
</template>

<style scope>
.chart {
  height: 700px;
}

.el-row {
  margin-bottom: 20px;
}

.el-row:last-child {
  margin-bottom: 0;
}

.el-col {
  border-radius: 4px;
}

.grid-content {
  border-radius: 4px;
  min-height: 36px;
}
</style>