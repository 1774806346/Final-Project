module.exports = {
  presets: [
    '@vue/cli-plugin-babel/preset'
  ],
  plugins: [
    ["prismjs", {
      "languages": ["javascript", "Java","css", "markup"],
      "plugins": ["line-numbers"],
      "theme": "tomorrow",
      "css": true
    }]
  ]
}
