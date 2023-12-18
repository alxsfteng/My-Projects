<template>
  <Video></Video>
  <div class="main-container">
    <div id="login" class="text-center view-background">
      <div class="form-container">
        <form v-on:submit.prevent="login">
          <h1>Please Sign In</h1>

          <div role="alert" v-if="invalidCredentials">
            Invalid username and password!
          </div>
          <div role="alert" v-if="this.$route.query.registration">
            Thank you for registering, please sign in.
          </div>
          <div class="form-input-group">
            <div class="username-container">
              <label for="username">Username</label>
            </div>
            <div class="password-container">

            </div>
            <input type="text" id="username" v-model="user.username" required autofocus />
          </div>
          <div class="form-input-group">
            <div class="password-container">
              <label for="password">Password</label>
            </div>
            <input type="password" id="password" v-model="user.password" required />
          </div>
          <div class="button-container">
            <button type="submit">Sign in</button>
          </div>
          <p class="login-link">
            <router-link v-bind:to="{ name: 'register' }">Need an account? Sign up.</router-link>
          </p>
        </form>
      </div>

    </div>
  </div>
</template>

<script>
import Video from "../components/Video.vue"
import authService from "../services/AuthService";

export default {
  components: {
    Video
  },
  data() {
    return {
      user: {
        username: "",
        password: ""
      },
      invalidCredentials: false
    };
  },
  methods: {
    login() {
      authService
        .login(this.user)
        .then(response => {
          if (response.status == 200) {
            this.$store.commit("SET_AUTH_TOKEN", response.data.token);
            this.$store.commit("SET_USER", response.data.user);
            this.$router.push("/");
            console.log(response.data)
          }
        })
        .catch(error => {
          const response = error.response;

          if (response.status === 401) {
            this.invalidCredentials = true;
          }
        });
    }
  }
};
</script>

<style scoped>
body {
  display: flex;
  width: 90rem;
  padding: 10.875rem 0rem 10.92813rem 0rem;
  justify-content: center;
  align-items: center;
}

.main-container {
  position: relative;
  display: grid;
  padding: 41px 24.839px 42.88px 25.161px;
  flex-direction: column;
  justify-content: center;
  place-items: center;
  font-style: Poppins;
  z-index: 5;
}
.video-overlay {
  position: absolute;
  top: 0;
  left: -50%;
  right: 50%;
  bottom: 0;
  z-index: 2; /* Set a higher z-index to position it above other elements */
}

.brand-logo {
  position: relative;
  z-index: 6; /* Set a higher z-index than the other elements */
}

.form-input-group {
  margin-bottom: 1rem;
}

label {
  margin-right: 0.5rem;
}
.form-container {
  margin-top: 0px;
  display: inline-flex;
  padding: 41px 24.839px 42.88px 25.161px;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  border-radius: 20.017px;
  border: 3px solid #000;
  background:#DCFD00;
  box-shadow: 6px 6px 0px 4px #000;
  z-index: 3;

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

h1 {
  margin: auto;
  width: 261.161px;
  height: 38.766px;
  color: #000;


  font-size: 28.564px;
  font-style: normal;
  font-weight: 800;
  line-height: 30.605px;
}

@media (max-width: 768px) {
  body {
    padding: 1rem; /* Add some padding for better spacing */
  }

  .main-container {
    padding: 20px; /* Adjust padding for smaller screens */
    margin-top: -1%;
    z-index: 7;
  }

  .video-overlay {
    left: 0;
    right: 0;
    z-index: 6; /* Set a z-index lower than form-container to ensure it's behind */
  }

  .form-container {
    position: relative;
    z-index: 10; /* Set a higher z-index */
    margin-top: 10px; /* Adjust as needed to ensure proper overlay */
    padding: 20px; /* Adjust padding for smaller screens */
  }

  .button-container button[type="submit"] {
    width: 100%;  /*Make the button full width on smaller screens */
  }

  .login-link {
    width: 90%; /* Make the login link full width on smaller screens */
  }

  #username,
  #password {
    width: 100%; /* Make input fields full width on smaller screens */
  }

  h1 {
   /* width: 100%;  Make heading full width on smaller screens */
  }
}


</style>