Vue.createApp({
    data() {
        return {
            cardNumber: "",
            cvv:"",
        }
    },

    created(){

    },

    methods:{

        paymentCard () {

                axios.post('/api/payments',{ number: this.cardNumber, cvv: this.cvv, amount: 600 , description:"compra en origami"})
                .then(response => {
                    Swal.fire('Pago realizado','','success')
                    //.then(res=> window.location.href = "/web/accounts.html", 5000)
                })
                .catch (error => {
                    Swal.fire({
                        icon:'error',
                        text: error.response.data,
                        })
                })
        },


    },
    computed : {

    }

    }).mount('#app')