<script setup>
import { ref, onMounted, computed } from 'vue';
import { empJobDataApi, empGenderDataApi } from '@/api/report';

const empJobJobList = ref([]);
const empJobDataList = ref([]);
const empGender = ref();

// 钩子函数
onMounted(() => {
  search();
})

//查询
const search = async () => {
  let result;
  result = await empJobDataApi();
  if (result.code) {
    empJobJobList.value = result.data.jobList;
    empJobDataList.value = result.data.dataList;
  }

  result = await empGenderDataApi();
  if (result.code) {
    empGender.value = result.data;
  }
}

const optionEmpJob = computed(() => {
  return {
    title: {
      text: '员工职位统计'
    },
    xAxis: {
      type: 'category',
      data: empJobJobList.value
    },
    yAxis: {
      type: 'value'
    },
    series: [
      {
        data: empJobDataList.value,
        type: 'bar'
      }
    ]
  }
});

const optionEmpGender = computed(() => {
  return {
    title: {
      text: '员工性别统计'
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
        data: empGender.value
      }
    ]
  }
});
</script>

<template>
  <el-row :gutter="20">
    <el-col :span="12">
      <v-chart class="chart" :option="optionEmpJob" />
    </el-col>
    <el-col :span="12">
      <v-chart class="chart" :option="optionEmpGender" />
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