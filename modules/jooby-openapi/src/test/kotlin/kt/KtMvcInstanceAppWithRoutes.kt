package kt

import io.jooby.Kooby

class KtMvcInstanceAppWithRoutes : Kooby({

  routes {
    decorator(PassDecorator)
    mvc(KtController())
  }
})
