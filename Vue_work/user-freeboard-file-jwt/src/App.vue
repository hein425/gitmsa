<script setup>
import { RouterLink, RouterView } from 'vue-router'
</script>

<template>
  <header>
    <div class="p-5 bg-teal-500 text-white text-3xl">
      <nav class="flex justify-between">
        
        <div class="flex space-x-5">
        
        <div>
          <RouterLink to="/">Home</RouterLink>
        </div>
        <div>
          <RouterLink to="/user">User</RouterLink>
        </div>
        <div class="group relative">
          <RouterLink to="/freeboardlist">FreeBoard</RouterLink>
          <div class="opacity-0 
                    group-hover:opacity-100 
                    transition absolute 
                    bg-teal-500 
                    p-5 top-8
                    -left-5
                    rounded">
            <div>
              <RouterLink to="/freeboardlist">FreeBoardList</RouterLink>
            </div>
            <div>
              <RouterLink to="/freeboardinput">FreeBoardinput</RouterLink>
            </div>
          </div>
        </div>
        <div>
          <RouterLink to="/fileupload">fileupload</RouterLink>
        </div>
        <div>
          <RouterLink to="/filedownload">filedownload</RouterLink>
        </div>


      </div>

      <div class="flex space-x-5">

        <div>
          <RouterLink to="/join">join</RouterLink>
        </div>
        <div>
          <RouterLink to="/login">login</RouterLink>
        </div>

      </div>


      </nav>
    </div>
  </header>

  <RouterView />
</template>

<style scoped></style>



<!--  JWT 토큰을 가지고 프리보드에 요청을 함.  -->
