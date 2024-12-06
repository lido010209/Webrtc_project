<template>
    <div class="fixed-top">
        <Navbar />
    </div>
    <div class="mt-5">
        <div class="position-relative">
            <img src="@/assets/home/home.png" class="img-fluid w-100">
            <div class="position-absolute start-0 bottom-0 m-4 col-3 col-lg-2 moving">
                <img src="@/assets/home/chibi.png" 
                class="img-fluid w-50"><br>
                <img src="@/assets/home/luna.png" class="img-fluid w-50">
            </div>
            <div class="position-absolute moving-tech text-end top-0 w-75 d-none d-lg-block mt-5" style="color: ">
                <img src="@/assets/home/connection.png" class="img-fluid w-50 mt-5">
            </div>
        </div>
    </div>
</template>

<script>
import Navbar from '@/components/navbar/Navbar.vue'
export default {
    name: "Home-component",
    components: {
        Navbar,
    }, 
}
</script>

<style>
@keyframes bounce {
  0%, 100% {
    transform: translateY(0);
  }
  50% {
    transform: translateY(-30%);
  }
}

@keyframes blink {
  0%, 50%, 100% {
    opacity: 1;
  }
  25%, 75% {
    opacity: 0.5;
  }
}

.moving {
    animation: bounce 3s infinite
}
</style>
