<template>
  <div class="stock-display">
    <div class="top-border"></div>
    <div class="stock-data">
      <div v-for="(item, index) in stockData" :key="index" class="index-item">
        <span>{{ item.name }}</span>
        <span :class="{ 'text-red': item.change > 0, 'text-green': item.change < 0 }">
          {{ item.price }} {{ item.change > 0 ? '▲' : '▼' }}
        </span>
        <span :class="{ 'text-red': item.change > 0, 'text-green': item.change < 0 }">
          {{ item.change > 0 ? '+' : '' }}{{ item.changePercent }}%
        </span>
      </div>
    </div>
    <div class="bottom-border"></div>
  </div>
</template>

<script>
export default {
  name: 'StockDisplay',
  data() {
    return {
      stockData: [
        { name: '上证指数', price: '3316.11', change: 37.55, changePercent: '1.14' },
        { name: '深证成指', price: '10082.33', change: 183.76, changePercent: '1.85' },
        { name: '创业板指', price: '1986.41', change: 38.62, changePercent: '1.98' },
        { name: '中证500', price: '5740.33', change: 109.05, changePercent: '1.93' }
      ]
    };
  },
  mounted() {
    // 模拟数据动态变化
    this.interval = setInterval(() => {
      this.updateMockData();
    }, 5000);
  },
  beforeDestroy() {
    if (this.interval) {
      clearInterval(this.interval);
    }
  },
  methods: {
    updateMockData() {
      this.stockData = this.stockData.map(item => {
        // 随机小幅波动
        const randomChange = (Math.random() - 0.5) * 10;
        const newPrice = parseFloat(item.price) + randomChange;
        const newChange = parseFloat(item.change) + randomChange;
        const newChangePercent = ((newChange / (newPrice - newChange)) * 100).toFixed(2);

        return {
          ...item,
          price: newPrice.toFixed(2),
          change: newChange.toFixed(2),
          changePercent: newChangePercent
        };
      });
    }
  }
};
</script>


<style scoped>
.stock-display {
  width: 100%;
  margin-top: 37px;
}

.top-border,
.bottom-border {
  border-top: 2px solid black;
  width: 100%;
}

.stock-data {
  display: flex;
  flex-direction: column;
  padding: 10px 0;
  font-weight: bold;
}

.index-item {
  display: flex;
  justify-content: space-around;
  font-size: large;
  margin-bottom: 15px;
  font-weight: bold;
}

.loading, .error {
  text-align: center;
  padding: 20px;
  color: #666;
}

.text-red {
  color: red;
}

.text-green {
  color: green;
}
</style>