<template>
    <div>
        <Navbar />
    </div>
    <div class="p-3">
        <div class="container-fluid d-flex flex-wrap">
            <a href="#" class="p-2 col-12 col-sm-6 col-lg-4 text-decoration-none">
                <div class="border rounded p-3">
                    <div class="d-flex justify-content-between w-100">
                        <img src="@/assets/calling/frame1.png" class="img-fluid col-3">
                        <img src="@/assets/calling/frame2.png" class="img-fluid col-3">
                    </div>
                    <div class="d-flex justify-content-center flex-wrap">
                        <div class="w-100 text-center">
                            <img src="@/assets/user/user.png" class="img-fluid w-25">
                        </div>
                        <h3 class="fw-bold">Luna</h3>
                    </div>
                    <div class="d-flex justify-content-between">
                        <img src="@/assets/calling/frame3.png" class="img-fluid col-3">
                        <img src="@/assets/calling/frame4.png" class="img-fluid col-3">
                    </div>
                </div>
            </a>
            
        </div>
    </div>
</template>

<script>
import Navbar from '@/components/navbar/Navbar.vue'
export default {
    name,
    components: {
        Navbar
    }
}
</script>
