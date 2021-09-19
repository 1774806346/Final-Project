import router from "./router";
// Determine the login based on the parameters in the routing configuration file
router.beforeEach((to, from, next) => {
  if (to.matched.some(record => record.meta.requireAuth)) {
    const token = localStorage.getItem("token")

    if (token) {
      if (to.path === '/login') {
      } else {
        next()
      }
    } else {
      next({
        path: '/login'
      })
    }
  } else {
    next()
  }
})
