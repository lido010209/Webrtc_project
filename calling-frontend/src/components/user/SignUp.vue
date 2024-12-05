<template>
  <div class="container-fluid d-flex flex-wrap justify-content-center mt-3">
    <div class="border border-success rounded bg-white shadow-lg col-12 col-md-7 pb-3">
      <h1 class="w-100 text-center text-white" style="background-color: darkgreen">
        회원가입
      </h1>
      <form @submit.prevent="formSignup" class="w-100 ps-3 pe-3">
        <div class="form-group">
          <label for="username" class="form-label">사용자 이름 </label><br />
          <input type="text" v-model="username" class="form-control" required />
        </div>
        <br />
        <div class="form-group">
          <label for="password" class="form-label">비밀번호 </label><br />
          <input type="password" v-model="password" class="form-control" required />
        </div>
        <br />
        <div class="form-group">
          <label for="pwCheck" class="form-label">비밀번호 확인 </label><br />
          <input type="password" v-model="pwCheck" class="form-control" required />
        </div>
        <br />
        <div class="form-group">
          <label for="name" class="form-label">성명 </label><br />
          <input type="text" v-model="name" class="form-control" required />
        </div>
        <br />
        <a href="/lu/login" class="w-100 link-secondary"
          >이미 회원이신가요?</a
        >
        <br /><br />
        <div class="col-12 text-center">
          <button type="submit" class="btn btn-success mb-2">회원가입</button>
        </div>
      </form>
    </div>
  </div>
</template>

<script>
import UserService from '@/service/user/UserService';

export default {
    // name: 'Sign-up',
    data(){
      return {
        username: "",
        password: "",
        pwCheck: "",
        name: "",
      }
    },
    methods:{
      formSignup(){

        const requestData = {
          username: this.username,
          password: this.password,
          pwCheck:this.pwCheck,
          name: this.name,
        }

        UserService.newUser(requestData).then(()=>{
          alert("Your account is created successful^^")
          this.$router.push('/lu/login');
        }).catch(e=>{
          alert(`Error: ${e.response.data}`);
        })
      }
    },
    mounted(){
      const token= localStorage.getItem('token');
      if (token) this.$router.push('/lu');
    }
}
</script>
