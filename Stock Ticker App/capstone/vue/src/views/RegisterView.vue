<template>
  <Video></Video>
  <div class="main-container">
    <div id="register" class="text-center view-background">
      <div class="form-container">
        <form v-on:submit.prevent="register">
          <h1>Create Account</h1>
          <div role="alert" v-if="registrationErrors">
            {{ registrationErrorMsg }}
          </div>
          <div class="form-input-group">
            <div class="username-container">
              <label for="username">Username</label>
            </div>
            <input type="text" id="username" v-model="user.username" required autofocus />

          </div>
          <div class="form-input-group">
            <div class="password-container">
              <label for="password">Password</label>
            </div>
            <input type="password" id="password" v-model="user.password" required />
          </div>
          <div class="form-input-group">
            <div class="confirmPassword-container">
              <label for="confirmPassword">Confirm Password</label>
            </div>
            <input type="password" id="confirmPassword" v-model="user.confirmPassword" required />
          </div>
          <div class="button-container">
            <button type="submit">Create Account</button>
          </div>
          <p class="login-link">
            <router-link v-bind:to="{ name: 'login' }">Already have an account? Log in.</router-link>
          </p>
        </form>
      </div>
    </div>
    <div class="disclaimer-container">
      <p>
        *This mock stock trading game is designed for educational and entertainment purposes only. Participants engage in
        trading using simulated money, which does not involve any real financial transactions or obligations.
      </p>
    </div>
  </div>
</template>

<script>
import authService from '../services/AuthService';
import Video from '../components/Video.vue';


export default {
  components: {
    Video
  },
  data() {
    return {
      user: {
        username: '',
        password: '',
        confirmPassword: '',
        role: 'user',
      },
      registrationErrors: false,
      registrationErrorMsg: 'There were problems registering this user.',
    };
  },
  methods: {
    register() {
      if (this.user.password != this.user.confirmPassword) {
        this.registrationErrors = true;
        this.registrationErrorMsg = 'Password & Confirm Password do not match.';
      } else {
        authService
          .register(this.user)
          .then((response) => {
            if (response.status == 201) {
              this.$router.push({
                path: '/login',
                query: { registration: 'success' },
              });
            }
          })
          .catch((error) => {
            const response = error.response;
            this.registrationErrors = true;
            if (response.status === 400) {
              this.registrationErrorMsg = 'Bad Request: Validation Errors';
            }
          });
      }
    },
    clearErrors() {
      this.registrationErrors = false;
      this.registrationErrorMsg = 'There were problems registering this user.';
    },
  },
};
</script>

<style scoped>
.main-container {
  position: relative;
  display: grid;
  padding: 45px 24.839px 42.88px 25.161px;
  flex-direction: column;
  justify-content: center;
  place-items: center;
  font-style: Poppins;
  z-index: 3;
}

.video-overlay {
  position: absolute;
  top: 0;
  left: -50%;
  right: 50%;
  bottom: 0;
  /* z-index: 1;  */
  /* Set a higher z-index to position it above other elements */
}

.brand-logo {
  position: relative;
  /* Makes the brand logo container a positioning context */
  z-index: 2;
}

.form-input-group {
  margin-bottom: 1rem;

}

label {
  margin-right: 0.5rem;
}

.form-container {
  display: inline-flex;
  padding: 41px 24.839px 42.88px 25.161px;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  border-radius: 20.017px;
  border: 3px solid #000;
  background: #DCFD00;
  box-shadow: 6px 6px 0px 4px #000;
  z-index: 3;
  /* Set a higher z-index than the other elements so that this is not obscured on mobile views */
}

.button-container button[type="submit"] {
  display: flex;
  width: 325px;
  height: 72px;
  padding: 8px 12px;
  justify-content: center;
  align-items: center;
  gap: 8px;
  border-radius: 16px;
  border: 4px solid #000;
  font-style: Poppins;
  font-size: large;
  background: #4CBB17;
  box-shadow: -16px -16px 0px 0px rgba(0, 0, 0, 0.25) inset;
}

.login-link {
  display: flex;
  width: 325px;
  height: 48px;
  padding: 8px 12px;
  justify-content: center;
  align-items: center;
  gap: 8px;
  border-radius: 16px;
  border: 4px solid #000;
  text-decoration: none;
  background: #FEF9ED;
  box-shadow: -8px -8px 0px 0px rgba(0, 0, 0, 0.25) inset;
}

#username {
  width: 281.613px;
  height: 41.338px;
  flex-shrink: 0;
  border-radius: 6.89px;
  border: 1.722px solid var(--primary-base, #6B4EFF);
  background: var(--sky-white, #FFF);
}

#password {
  width: 281.613px;
  height: 41.338px;
  flex-shrink: 0;
  border-radius: 6.89px;
  border: 1.722px solid var(--primary-base, #6B4EFF);
  background: var(--sky-white, #FFF);
}

#confirmPassword {
  width: 281.613px;
  height: 41.338px;
  flex-shrink: 0;
  border-radius: 6.89px;
  border: 1.722px solid var(--primary-base, #6B4EFF);
  background: var(--sky-white, #FFF);
}

.username-container {
  display: flex;
  align-items: flex-start;
  gap: 8.612px;
  align-self: stretch;
}

.password-container {
  display: flex;
  align-items: flex-start;
  gap: 8.612px;
  align-self: stretch;
}

.confirmPassword-container {
  display: flex;
  align-items: flex-start;
  gap: 8.612px;
  align-self: stretch;
}

h1 {
  width: 261.161px;
  height: 38.766px;
  color: #000;
}

.disclaimer-container {
  font-size: 28.564px;
  font-style: normal;
  font-weight: 800;
  line-height: 30.605px;
}

.disclaimer-container {
  width: 339px;
  height: 108px;
  flex-shrink: 0;
  color: #FFF;
  font-size: 14px;
  font-style: normal;
  font-weight: 500;
  line-height: 24px;
}

.form-container h1 {
  text-align: center;
}

@media (max-width: 768px) {
  .main-container {
    padding: 50px;
    /* Adjust padding for smaller screens */
    z-index: 6;
  }

  .video-overlay {
    left: 0;
    /* Adjust the overlay position to cover the entire screen */
    right: 0;
    z-index: 5;
    /* Set a z-index lower than form-container to ensure it's behind */
  }

  .form-container {
    position: relative;
    /* Make the form-container stack on top of the video overlay */
    z-index: 10;
    /* Set a higher z-index */
    margin-top: -5%;
    /* Adjust as needed to ensure proper overlay */
    padding: 20px;
    width: 85vw;
  }
  .form-container > * {
    margin-left: 10px;
  }

  .button-container{
    margin-right: 15px;
    width: 310px;
  }

  .login-link{
    margin-right: 15px;
    width: 300px;
  }

  .form-container > * {
    padding-left: 10px;
  }

  .brand-logo {
    z-index: 6;
    /* Set a higher z-index than the other elements */
  }
}</style>
