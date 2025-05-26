<template>

<header>

</header>
<main>
  <router-view />
</main>

</template>

<style>
html, body, #app {
  height: 100%;
  margin: 0;
  padding: 0;
}
body {
  min-height: 100vh;
  width: 100vw;
  box-sizing: border-box;
}
#app {
  min-height: 100vh;
  width: 100vw;
  display: flex;
  flex-direction: column;
}
main {
  flex: 1;
  min-height: 0;
  width: 100vw;
  display: flex;
  flex-direction: column;
}
</style>

