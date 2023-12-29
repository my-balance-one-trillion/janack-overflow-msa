<template>
  <div class="flex flex-col justify-center min-h-screen py-6 bg-white sm:py-12">
    <div class="relative py-3 sm:max-w-xl sm:mx-auto">
      <div
        class="absolute inset-0 transform -skew-y-6 shadow-lg bg-gradient-to-r from-green-700 to-red-600 sm:skew-y-0 sm:-rotate-6 sm:rounded-3xl">
      </div>

      <div class="relative px-4 py-10 shadow-lg bg-gray-50 sm:rounded-3xl sm:p-20">
        <div class="max-w-md mx-auto">
          <div>
            <h1 class="text-2xl font-semibold text-center">비밀번호 변경</h1>
          </div>

          <div class="divide-y divide-gray-200">
            <div class="py-8 space-y-4 text-base leading-6 text-gray-700 sm:text-lg sm:leading-7">
              <div class="relative pb-3" @keyup.enter="mailSend">
                <div class="relative">
                  <input autocomplete="off" id="email" name="email" type="text"
                    class="w-full h-10 text-gray-900 placeholder-transparent border-t-0 border-b-2 border-l-0 border-r-0 border-red-700 focus:ouline-none focus:ring-0 focus:border-main-red peer bg-gray-50 focus:outline-none focus:borer-rose-600"
                    v-model="state.input.email" 
                    placeholder="Email" />
                  <label for="email"
                    class="absolute left-0 -top-3.5 text-gray-600 text-sm peer-placeholder-shown:text-base peer-placeholder-shown:text-gray-440 peer-placeholder-shown:top-2 transition-all peer-focus:-top-3.5 peer-focus:text-gray-600 peer-focus:text-sm">Email</label>
                </div>

              </div>

              <p class="text-sm text-center">회원님의 메일 주소로 임시 비밀번호가 발송됩니다.</p>
          
              <div class="relative flex justify-center pt-2">
                <button class="py-1 text-white bg-red-700 rounded-3xl px-28" @click="login">
                  메일발송
                </button>
              </div>
            </div>
          </div>
        </div>
        <div class="flex justify-center">
          <p class="text-sm">비밀번호가 떠오르셨나요?</p>
          <router-link to="/login" class="pl-2 text-sm text-red-700">로그인</router-link>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { reactive } from "vue";
import axios from "axios";
import { useRouter } from "vue-router";

axios.defaults.withCredentials = true;

const router = useRouter();
const state = reactive({
  input: {
    email: ""
  },
});

const exMail = /^[A-Za-z0-9_\.\-]+@[A-Za-z0-9\-]+\.[A-Za-z0-9\-]+/;

const login = async () => {
  try {
      if(exMail.test(state.input.email) === false) {
        alert('이메일 형식이 올바르지 않습니다.');
        return false;
      }

    const res = await axios.post(
      "/mailPass",
      state.input,
      {
        headers: {
          "Content-Type": "application/json",
          "X-Requested-With": "XMLHttpRequest",
        },
      }
    );

    console.log(res);
    window.alert(res.data);

    router.push("/login");

  } catch (error) {
    console.log("발송 실패", error);
    window.alert(error.response.data);
  }
};
</script>