import { inject } from "@angular/core";
import { ActivatedRouteSnapshot, CanActivateChildFn, CanActivateFn, Router, RouterStateSnapshot } from "@angular/router";
import { AuthService } from "./auth.service";

export const canActivate: CanActivateFn = (
  route: ActivatedRouteSnapshot,
  state: RouterStateSnapshot
) => {
  const authService = inject(AuthService);
  const router = inject(Router);

  let isLoggedIn = authService.checkLogin();
  if(!isLoggedIn) {
    router.navigate(['/login'])
  }
  return isLoggedIn;
};

export const canAuthorizeMaker: CanActivateFn = (
  route: ActivatedRouteSnapshot,
  state: RouterStateSnapshot
) => {
  const authService = inject(AuthService);
  const router = inject(Router);

  let isAuthorized = authService.checkRole("MAKER");
  let isLoggedIn = authService.checkLogin();
  return isLoggedIn && isAuthorized;
};

export const canAuthorizeChecker: CanActivateFn = (
  route: ActivatedRouteSnapshot,
  state: RouterStateSnapshot
) => {
  const authService = inject(AuthService);
  const router = inject(Router);

  let isAuthorized = authService.checkRole("CHECKER");
  let isLoggedIn = authService.checkLogin();
  return isLoggedIn && isAuthorized;
};

export const canActivateChild: CanActivateChildFn = (route: ActivatedRouteSnapshot, state: RouterStateSnapshot) => canActivate(route, state);
