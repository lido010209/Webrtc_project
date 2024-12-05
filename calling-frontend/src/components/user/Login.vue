<template>
  <div
    class="mt-5 container-fluid border border-success rounded bg-white shadow-lg d-flex flex-wrap justify-content-center col-12 col-md-7"
  >
    <h1 class="w-100 text-center text-white" style="background-color: darkgreen">
      로그인
    </h1>
    <form @submit.prevent="formLogin" class="w-100 ps-3 pe-3">
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
      <a href="/lu/signup" class="w-100 link-secondary"
        >아직 회원이 아니신가요?</a
      >
      <br /><br />
      <div class="col-12 text-center">
        <button type="submit" class="btn btn-success mb-2">로그인</button>
      </div>
    </form>
    <div class="w-100 text-center d-flex flex-wrap justify-content-center">
      <hr class="w-25" />
      <span class="m-1"><b>또는</b></span>
      <hr class="w-25" />
    </div>
    <div class="d-flex w-100 justify-content-center pb-5">
      <div class="col-2 d-flex justify-content-center">
        <div
          class="border-radius-sm rounded-circle border-2 border border-dark"
          style="height: 45px; width: 45px"
        >
          <a href="/oauth2/authorization/naver" id="naver" class="link-image">
            <img
              src="@/assets/user/naver.png"
              class="img-fluid"
              style="border-radius: 50%"
            />
          </a>
        </div>
      </div>
      <div class="col-2 d-flex justify-content-center">
        <div
          class="border-radius-sm rounded-circle border-2 border border-dark"
          style="height: 45px; width: 45px"
        >
          <a href="/oauth2/authorization/kakao" id="kakao" class="link-image">
            <img
              src="@/assets/user/kakao.png"
              class="img-fluid"
              style="border-radius: 50%"
            />
          </a>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import UserService from '@/service/user/UserService'

export default {
    name: 'Log-in',
    data(){
        return {
            username: "",
            password: "",
        }
    },
    methods:{
        formLogin(){
            const requestData = {
                username:this.username,
                password: this.password,
            }

            UserService.getToken(requestData).then(res=>{
                localStorage.setItem('token', res.data.token);
                alert("Login successful ^^");
                this.$router.push('/lu');
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
