void my_memset(char *str, char val, int strl)
{
  int i;
  for (i = 0; i < strl; i++)
    str[i] = val;
}
