Vue.createApp({
    data() {
        return {
            cardNumber: "",
            cvv:"",
            card1Number:"",
            card2Number:"",
            card3Number:"",
            card4Number: "",
            month: "",
            year: "",
            yearFix:"",
            monthFix: ""
        }
    },

    created(){

    },

    methods:{

        paymentCard () {

                axios.post('/api/payments',{ number: this.cardNumber, cvv: this.cvv, amount: 1600, month:this.monthFix, year:this.yearFix, description:"compra en origami"})
                .then(response => {
                    Swal.fire('Pago realizado','','success')
                    .then(res=> window.location.href = "", 5000)
                })
                .catch (error => {
                    Swal.fire({
                        icon:'error',
                        text: error.response.data
                        })
                })
                console.log(this.cardNumber)
                console.log(this.yearFix)
                console.log(this.monthFix)
        },


    },
    computed : {
        fixNumber(){
            this.cardNumber = this.card1Number+"-"+this.card2Number+"-"+this.card3Number+"-"+this.card4Number
        },
        fixYear(){
            if(this.year != ""){
                this.yearFix= Number(20+this.year)
            }
        },
        fixmonth(){
            this.monthFix = Number(this.month)
        }
    }

    }).mount('#app')