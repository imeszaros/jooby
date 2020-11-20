package kt

import io.jooby.Route

object PassDecorator : Route.Decorator {
  override fun apply(next: Route.Handler): Route.Handler {
    return next
  }
}
