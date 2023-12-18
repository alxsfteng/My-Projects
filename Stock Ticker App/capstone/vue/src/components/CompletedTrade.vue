<template>
    <div>
        <div class="container">
            <div class="confetti"></div>
            <div class="confetti"></div>
            <div class="confetti"></div>
            <div class="confetti"></div>
            <div class="confetti"></div>
            <div class="confetti"></div>
            <div class="confetti"></div>
            <div class="confetti"></div>
            <div class="confetti"></div>
            <div class="confetti"></div>
        </div>
        <div class="container-receipt">
            <p class="trade-info">
                <span v-if="isBuyTrade" class="buy">Your BUY trade has been  processed for:</span>
                <span v-else-if="isSellTrade" class="sell">Your SELL trade has been processed</span>
            </p>
            <h1 class="stock-company-name">{{ stockSymbol }}</h1>
            <div class="company-name-modal">{{ companyName }}</div>
            <p class="price-paid">Your real-time stock price was ${{ tradeData.stockPrice.toFixed(2) }}</p>
            <div class="price-paid">
                You're remaining cash balance is:
                <span class="balance-after-trade">${{ tradeData.playerUpdatedBalance }}</span>
            </div>
            <div class="button-modal">
                <button @click="closeModalAndCelebrate">Stonk On!</button>
            </div>


        </div>
    </div>
</template>
<script>

export default {
    computed: {
        tradeData() {
            return this.$store.state.tradeData;
        },
        isBuyTrade() {
            return this.tradeData.tradeTypeId === 1;
        },
        isSellTrade() {
            return this.tradeData.tradeTypeId === 2;
        },
        stockInfoList() {
            return this.$store.state.stockInfoList;
        },
        companyName() {
            const symbol = this.tradeData.stockSymbol;
            const foundCompany = this.stockInfoList.find(company => company.symbol === symbol);
            return foundCompany ? foundCompany.companyName : "";
        },
        stockSymbol() {
            return this.tradeData.stockSymbol;
        },

        beforeRouteLeave(to, from, next) {
            this.$store.commit('SET_TRADE_COMPLETED', false);
            next();
            return null
        },

    },
    methods: {
        closeModalAndCelebrate() {
            console.log('Close button clicked!');
            this.$emit('modal-closed');
            this.$router.push('/');
        },
    },
};
</script>
<style>
.container-receipt {
    width: 35vw;
    height: 40vh;
    flex-shrink: 0;
    border-radius: 1rem;
    border: 4px solid var(--Text-Subtitle, #000);
    background: var(--Surface-01, #FFF);
    box-shadow: 8px 8px 4px 0px rgba(0, 0, 0, 0.25) inset;
    position: fixed;
    top: 50%;
    left: 50%;
    transform: translate(-50%, -50%);
    background: #fff;
    padding: 20px;
    border-radius: 8px;
    box-shadow: 0 0 10px rgba(0, 0, 0, 0.2);
    text-align: center;
}

.confetti {
    width: 15px;
    height: 15px;
    background-color: #f2d74e;
    position: absolute;
    left: 50%;
    animation: confetti 5s ease-in-out -2s infinite;
    transform-origin: left top;
}

.confetti:nth-child(1) {
    background-color: #f2d74e;
    left: 10%;
    animation-delay: 0;
}

.confetti:nth-child(2) {
    background-color: #95c3de;
    left: 20%;
    animation-delay: -5s;
}

.confetti:nth-child(3) {
    background-color: #ff9a91;
    left: 30%;
    animation-delay: -3s;
}

.confetti:nth-child(4) {
    background-color: #f2d74e;
    left: 40%;
    animation-delay: -2.5s;
}

.confetti:nth-child(5) {
    background-color: #95c3de;
    left: 50%;
    animation-delay: -4s;
}

.confetti:nth-child(6) {
    background-color: #ff9a91;
    left: 60%;
    animation-delay: -6s;
}

.confetti:nth-child(7) {
    background-color: #f2d74e;
    left: 70%;
    animation-delay: -1.5s;
}

.confetti:nth-child(8) {
    background-color: #95c3de;
    left: 80%;
    animation-delay: -2s;
}

.confetti:nth-child(9) {
    background-color: #ff9a91;
    left: 90%;
    animation-delay: -3.5s;
}

@keyframes confetti {
    0% {
        transform: rotateZ(15deg) rotateY(0deg) translate(0, 0);
    }

    25% {
        transform: rotateZ(5deg) rotateY(360deg) translate(-5vw, 20vh);
    }

    50% {
        transform: rotateZ(15deg) rotateY(720deg) translate(5vw, 60vh);
    }

    75% {
        transform: rotateZ(5deg) rotateY(1080deg) translate(-10vw, 80vh);
    }

    100% {
        transform: rotateZ(15deg) rotateY(1440deg) translate(10vw, 110vh);
    }
}

.container-receipt {
    background: #fff;
    padding: 20px;
    border-radius: 8px;
    box-shadow: 0 0 10px rgba(0, 0, 0, 0.2);
    text-align: center;
}

.trade-info {
    font-size: 18px;
    margin-bottom: 10px;
}

.buy {
    color: #4caf50;
}

.sell {
    color: #f44336;
}

.stock-company-name {
    font-family: Poppins;
    font-size: 44px;
    margin: 10px 0;
}

.company-name-modal {
    font-size: 16px;
    margin-bottom: 20px;
}

.price-paid {
    font-size: 20px;
    margin-bottom: 20px;
}

.update-balance-receipt {
    font-size: 16px;
    margin-bottom: 20px;
}

.balance-after-trade {
    font-size: 28px;
    font-weight: bold;
    color: #089b28;
}

.button-modal button {
    background: #3498db;
    color: #fff;
    padding: 10px 20px;
    border: none;
    border-radius: 4px;
    cursor: pointer;
}

</style>
