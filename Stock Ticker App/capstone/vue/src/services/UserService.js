import axios from 'axios';
  
export default {

    list() {
        return axios.get('/users');
      },
      //users is place holder till made on backend

}