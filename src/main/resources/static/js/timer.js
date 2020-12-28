(function () {
    const startingTime = 1;
    let time = startingTime * 60;

    const countDownEl = document.getElementById('timer');

    setInterval(updateCountdown, 1000);

    function updateCountdown(){
        const minutes = Math.floor(time / 60);
        let seconds = time % 60;

        countDownEl.innerHtml = `${minutes} : ${seconds}`;

        time--;
    }
})();