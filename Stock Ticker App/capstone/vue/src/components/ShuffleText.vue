<template>
    <div id="shuffle-text">
        <div  v-if="shuffling">
            <p v-for="(line, index) in lines" :key="index">
                <span v-for="(letter, letterIndex) in line" :key="letterIndex" class="letter" ref="letters">
                    {{ letter }}
                </span>
            </p>
        </div>
        <div class="shuffle-text" v-else>
            <p>{{ dynamicBalance }}</p>
        </div>
    </div>
</template>
  
<script>
class LetterShuffler {
    constructor(wrapper) {
        this.SHUFFLING_VALUES = [
            '5', '8', '$', '%',
            '2', '/', '(', ')',
            '1', '?', '9', '8',
            '>', '^', '°', '*',
            '#', '-', ':', ';', '6',
        ];
        this.wrapper = wrapper;
        this.letterToShown = this.wrapper.textContent.trim();
        this.timer = 1;
        this.duration = 500;
    }

    show() {
        this.timer = 300;
        this.animate();
    }

    animate() {
        this.timer++;
        if (this.timer < this.duration) {
            this.wrapper.textContent = this.SHUFFLING_VALUES[Math.floor(Math.random() * this.SHUFFLING_VALUES.length)];
            this.wrapper.style.transform = `scale(${(this.timer / this.duration) * .2})`;
            requestAnimationFrame(() => this.animate());
        } else {
            this.wrapper.textContent = this.letterToShown;
        }
    }
}

export default {
    props: {
        dynamicBalance: {
            type: String,
            required: true,
        },
    },
    data() {
        return {
            lines: ['          '],
            shuffling: false,
        };
    },
    methods: {
        shuffleLetters() {
        const letterElements = document.querySelectorAll('.letter');
        letterElements.forEach((letter) => {
            const letterShuffler = new LetterShuffler(letter);
            letterShuffler.show();
        });
        this.shuffling = true;

        // Increase the timeout to make the shuffle last longer
        setTimeout(() => {
            this.shuffling = false;
        }, this.duration * 5); // Set timeout to twice the duration of the shuffle animation
    
        },
    },
};
</script>
  
<style scoped>
.shuffle-text {
    color: var(--Text-Subtitle, #4CBB17);
    text-align: center;
    font-family: Poppins;
    font-size: 28px;
    font-style: normal;
    font-weight: 700;
    line-height: 2rem;
}
</style>
  