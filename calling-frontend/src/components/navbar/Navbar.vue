<template>
  <nav class="navbar navbar-expand-lg bg-body-tertiary">
    <div class="container-fluid ms-3 me-3">
      <a class="navbar-brand" href="#"
        ><img
          src="@/assets/navbar/logo.png"
          class="img-fluid"
          style="height: 40px"
      /></a>
      <button
        class="navbar-toggler"
        type="button"
        data-bs-toggle="collapse"
        data-bs-target="#navbarSupportedContent"
        aria-controls="navbarSupportedContent"
        aria-expanded="false"
        aria-label="Toggle navigation"
      >
        <span class="navbar-toggler-icon"></span>
      </button>
      <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <ul class="navbar-nav me-auto mb-2 mb-lg-0">
          <li class="nav-item me-2">
            <a class="nav-link active" aria-current="page" href="#"
              ><i class="fa-solid fa-globe me-1"></i> 루나 우주</a
            >
          </li>
          <li class="nav-item me-2">
            <a class="nav-link" href="#"
              ><i class="fa-solid fa-users me-1"></i> 친구</a
            >
          </li>
          <li class="nav-item me-2 d-lg-none">
            <a class="nav-link" href="/lu/profile"><i className="fa-solid fa-user me-1"></i> 프로필</a>
          </li>
          <li class="nav-item me-2 d-lg-none">
            <a class="nav-link" href="#" @click.prevent="logout"><i class="fa-solid fa-right-from-bracket me-1"></i> 로그아웃</a>
          </li>
          <li class="d-none d-lg-block">
            <form class="d-flex" role="search">
              <input
                class="rounded me-2"
                type="search"
                placeholder="검색"
                aria-label="Search"
              />
              <button class="btn btn-outline-success" type="submit">
                검색
              </button>
            </form>
          </li>
        </ul>
        <div class="d-none d-lg-block position-relative">
          <a href="#" class="img-fluid" @mouseenter="avatarIn">
            <img :src="avatar" style="height: 40px; border-radius: 50%"/>
            </a>
          <div :class="{ 'd-none': isVisible }" class="position-absolute end-0 top-100 rounded bg-light-subtle mt-3" style="width: 150px" @mouseleave="avatarOut">
            <div
              className="d-flex align-items-center justify-content-center border-bottom p-2"
            >
              <span><i class="fa-solid fa-circle-user fa-lg me-2"></i></span>
              <span>{{nameLogin}}</span>
            </div>
            <div class="d-flex align-items-center p-2">
              <i class="fa-solid fa-user me-2"></i>
              <a href="/lu/profile" class="text-decoration-none">프로필</a>
            </div>
            <div class="d-flex align-items-center p-2">
              <i class="fa-solid fa-right-from-bracket me-2"></i>
              <a href="#" @click.prevent="logout" class="text-decoration-none">로그아웃</a>
            </div>
          </div>
        </div>
      </div>
    </div>
  </nav>
</template>

<script>
import UserService from '@/service/user/UserService';
export default {
  name,
    data(){
        return {
            isVisible: true,
            nameLogin: null,
            avatar: require('@/assets/user/user.png'),
        };
    },
    methods:{
        avatarIn(){
            this.isVisible=false;
        },
        avatarOut(){
            this.isVisible = true
        },
        logout(){
            localStorage.removeItem('token');
            this.$router.push('/lu/login');
        },
    },
    mounted(){
        const token = localStorage.getItem('token');
        if (!token) {
            alert("Please login your account!!!");
            this.$router.push('/lu/login');
            return;
        }
        UserService.userLogin(token).then(res=>{
            this.nameLogin=res.data.name;
            this.avatar = res.data.avatar;
        })
    }
};
</script>
