<template>
    <div>
        <div class="leaderboard-container">
            <article class="leaderboard__profile" :class="{ 'is-hovered': isHovered }" @mouseover="handleMouseOver"
                @mouseleave="handleMouseLeave">

                <div class="profile-details">
                    <img :src="competitorPic" alt="Profile Picture" class="profile-pic-comp">
                    <div class="pic-and-name">
                        <p class="leaderboard__name">{{ playerInfo?.username }} in {{ playerInfo?.gameName }}</p>

                    </div>
                    <div class="leaderboard__value">{{ formatPercentage(playerInfo?.percentageChange) }}
                        <span class="bankroll">portfolio worth: ${{ playerInfo?.totalValue }}</span>
                    </div>

                </div>
            </article>
        </div>

    </div>
</template>
  
<script>
import competitorPic from '../assets/profile_pic_peace.png';

export default {
    props: ['playerInfo'],
    data() {
        return {
            competitorPic: competitorPic,
            isHovered: false,
        };
    },
    methods: {
        handleMouseOver() {
            this.isHovered = true;
        },
        handleMouseLeave() {
            this.isHovered = false;
        },
        formatPercentage(number) {
            if (typeof number !== 'number') {
                alert('You have tried to format something that is not a number');
            }
            const percentage = number * .01;
            return percentage.toLocaleString(undefined, { style: 'percent', minimumFractionDigits: 2 });
        }
    },
};
</script>
  
<style scoped>
.pic-and-name {
    display: flex;
    flex-direction: column;
}

.pic-and-name p {
    margin-left: 1.5rem;
}


.bankroll {
    color: var(--text-normal, #71717A);
    text-align: center;
    font-size: 1.5em;
    font-style: normal;
    font-weight: 400;
    font-family: Poppins;
}

.profile-details {
    display: flex;
    align-items: space-between;
    justify-content: center;
    width: 100%;

}

.profile-pic-comp {
    width: 40px;
    height: 40px;
    border-radius: 50%;
    box-shadow: 0 0 0 10px #ebeef3, 0 0 0 22px #f3f4f6;
}

.leaderboard__profile {
    display: flex;
    justify-content: center;
    margin: 2px;
    width: 170%;
    padding: 10px 10px 10px 10px;
    overflow: hidden;
    border-radius: 1rem;
    border: 2px solid #000;
    background: #DCFD00;
    box-shadow: 0px 4px 4px 0px rgba(0, 0, 0, 0.25) inset;
    cursor: pointer;
    transition: transform 0.25s cubic-bezier(.7, .98, .86, .98), box-shadow 0.25s cubic-bezier(.7, .98, .86, .98);
    background-color: #fff;
}

.leaderboard__profile.is-hovered {
    transform: scale(1.2);
    box-shadow: 0 9px 47px 11px rgba(51, 51, 51, 0.18);
}

.leaderboard__name {
    color: #000;
    font-weight: 500;
    font-size: 20px;
    letter-spacing: 0.5px;
    margin-bottom: 2px;
}

.leaderboard__value {
    color: #35d8ac;
    flex-grow: 2;
    font-weight: 500;
    font-size: 18px;
    text-align: right;
    display: flex;
    flex-direction: column;
    align-items: flex-end;

    &>span {
        opacity: .8;
        font-weight: 600;
        font-size: 13px;
        margin-left: 3px;
    }
}
</style>