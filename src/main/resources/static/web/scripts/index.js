Vue.createApp({
    data() {
        return {
            arrayClientes:[],
            json:[],
            sinClientes: "",
            client:{},
            person:"",
            emailSingIn: "",
            passwordSingIn : "",
            nameSingUp: "",
            lastNameSingUp:"",
            emailSingUp:"",
            passwordSingUp:""
        }
    },
    created(){
    },
    methods: {
                singIn () {
                    if(this.emailSingIn != "" && this.passwordSingIn != ""){
                        if(this.emailSingIn.includes("admin")){
                                axios.post('/api/login',`email=${this.emailSingIn}&password=${this.passwordSingIn}`,{headers:{'content-type':'application/x-www-form-urlencoded'}})
                                .then(response => window.location.href = "/web/admin-loan.html")
                                .catch (error => {
                                    console.log(error)
                                    Swal.fire({
                                        icon: 'error',
                                        text: 'Usuario o contraseña invalido',
                                    })
                                })
                            }else{
                                axios.post('/api/login',`email=${this.emailSingIn}&password=${this.passwordSingIn}`,{headers:{'content-type':'application/x-www-form-urlencoded'}})
                                .then(response => window.location.href = "/web/accounts.html")
                                .catch (error => {
                                    console.log(error)
                                    Swal.fire({
                                        icon: 'error',
                                        text: 'Usuario o contraseña invalido',
                                    })
                                })
                            }
                    }else {
                        Swal.fire({
                            icon: 'error',
                            text: 'Usuario o contraseña invalido',
                        })
                    }
                        
                },
                singUp (){
                    if(this.emailSingUp != "" && this.passwordSingUp != "" && this.nameSingUp != "" && this.lastNameSingUp != ""){
                        if(this.emailSingUp.includes("@admin")){
                            Swal.fire({
                                icon: 'error',
                                text: 'No es posible registrar un usuario "Admin"',
                            })
                        }else {
                            axios.post('/api/clients',`firstName=${this.nameSingUp}&lastName=${this.lastNameSingUp}&email=${this.emailSingUp}&password=${this.passwordSingUp}`,{headers:{'content-type':'application/x-www-form-urlencoded'}})
                            .then(
                                axios.post('/api/login',`email=${this.emailSingUp}&password=${this.passwordSingUp}`,{headers:{'content-type':'application/x-www-form-urlencoded'}})
                                .then(response => window.location.href = "/web/accounts.html")
                            )
                            .catch (error => {
                                console.log(error)
                                Swal.fire({
                                    icon: 'error',
                                    text: error.response.data,
                                })
                            })
                        }
                    }else {
                        Swal.fire({
                            icon: 'error',
                            text: 'Complete todos los campos',
                        })
                    }
                }    

    },
    computed: {
                





    },
}).mount('#app')