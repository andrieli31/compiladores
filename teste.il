.assembly extern mscorlib {}
.assembly _programa{}
.module _programa.exe

.class public _unica{
.method static public void _principal(){
.entrypoint
.locals init (int64 lado)
ldstr "digite um valor para lado: "
call void [mscorlib]System.Console::Write(string)
call string [mscorlib]System.Console::ReadLine()
call int64 [mscorlib]System.Int64::Parse(string)
stloc lado
ldstr "o valor digitado foi: "
call void [mscorlib]System.Console::Write(string)
ldloc lado
conv.r8
conv.i8
call void [mscorlib]System.Console::Write(int64)
ret
}
}