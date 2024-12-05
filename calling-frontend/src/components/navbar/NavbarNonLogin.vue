<template>
  <nav class="navbar navbar-expand-lg bg-body-tertiary">
    <div class="container-fluid ms-3 me-3">
      <a class="navbar-brand" href="#"><img src="@/assets/navbar/logo.png" class="img-fluid" style="height: 40px"></a>
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
            <a class="nav-link active" aria-current="page" href="#"><i class="fa-solid fa-globe"></i> 루나 우주</a>
          </li>
          <li class="nav-item me-2">
            <a class="nav-link" href="#"><i class="fa-solid fa-users"></i> 친구</a>
          </li>
          <li class="nav-item me-2 d-lg-none">
            <a class="nav-link" href="/lu/signup" target="_blank"><i class="fa-solid fa-user-plus"></i> 회원가입</a>
          </li>
          <li class="nav-item me-2 d-lg-none">
            <a class="nav-link" href="/lu/login" target="_blank"><i class="fa-solid fa-right-to-bracket"></i> 로그인</a>
          </li>
          <li class="d-none d-lg-block">
            <form class="d-flex" role="search">
                <input
                    class="rounded me-2"
                    type="search"
                    placeholder="검색"
                    aria-label="Search"
                />
                <button class="btn btn-outline-success" type="submit">검색</button>
            </form>
          </li>
        </ul>
        <div class="d-none d-lg-flex">
            <a href="/lu/signup" target="_blank" type="button" class="btn btn-success me-2">회원가입</a>
            <a href="/lu/login" target="_blank" type="button" class="btn btn-success">로그인</a>
        </div>
      </div>
    </div>
  </nav>
</template>

<script>
export default {
  name,
};
</script>
