export const promiseMiddleware = (store: any) => (next: any) => (action: any) => {
    console.error("promise middle ware", store, next, action)
}

export const localStorageMiddleware = (store: any) => (next: any) => (action: any) => {
    console.error("locale middle ware", store, next, action)
}