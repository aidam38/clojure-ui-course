module.exports = {
    content: ['./src/**/*.cljs', './resources/**/*.cljs'],
    theme: {
        extend: {},
    },
    variants: {},
    plugins: [
        require('@tailwindcss/typography')
    ],
}