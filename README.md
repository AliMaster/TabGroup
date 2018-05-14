TabGroup
========

更新一下，之前还是eclipse版的代码

---

很久以前拿的TabWidget的代码，自己改了改。

主要功能是，替换RadioGroup/RadioButton与ViewPager 组合中 前者的角色。因为RadioButton有图片和文本的时候上下位置不好控制

因为此控件继承自LinnearLayout ，所以用法基本相同，可以从xml中添加子控件，也可以addView(View v)动态添加子控件。具体实现自己看代码吧，不多。
