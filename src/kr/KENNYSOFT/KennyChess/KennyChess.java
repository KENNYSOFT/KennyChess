package kr.KENNYSOFT.KennyChess;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.text.method.LinkMovementMethod;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class KennyChess extends ActionBarActivity
{
	Typeface typeface;
	boolean isFocused,isMovable[][]=new boolean[10][10],isEnpassantable,isPlayer1KingsideCastlingable=true,isPlayer1QueensideCastlingable=true,isPlayer2KingsideCastlingable=true,isPlayer2QueensideCastlingable=true,isExiting;
	Button boardButton[][]=new Button[10][10];
	char boardChar[][]={
		{' ',' ',' ',' ',' ',' ',' ',' ',' ',' '},
		{' ','r','n','b','q','k','b','n','r',' '},
		{' ','p','p','p','p','p','p','p','p',' '},
		{' ',' ',' ',' ',' ',' ',' ',' ',' ',' '},
		{' ',' ',' ',' ',' ',' ',' ',' ',' ',' '},
		{' ',' ',' ',' ',' ',' ',' ',' ',' ',' '},
		{' ',' ',' ',' ',' ',' ',' ',' ',' ',' '},
		{' ','o','o','o','o','o','o','o','o',' '},
		{' ','t','m','v','w','l','v','m','t',' '},
		{' ',' ',' ',' ',' ',' ',' ',' ',' ',' '},
	};
	int delta[][][][]=new int[256][20][10][10],color[]={Color.parseColor("#FFD18B47"),Color.parseColor("#FFFFCE9E"),Color.parseColor("#FFFF0000"),Color.parseColor("#FFFFFF00"),Color.parseColor("#FFFF7F00"),Color.parseColor("#FF00FF00")},nowTurn=1,prvR,prvC;
	
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.kennychess);
		
		delta['r'][1][1][1]=0;
		delta['r'][1][1][2]=1;
		delta['r'][1][2][1]=0;
		delta['r'][1][2][2]=2;
		delta['r'][1][3][1]=0;
		delta['r'][1][3][2]=3;
		delta['r'][1][4][1]=0;
		delta['r'][1][4][2]=4;
		delta['r'][1][5][1]=0;
		delta['r'][1][5][2]=5;
		delta['r'][1][6][1]=0;
		delta['r'][1][6][2]=6;
		delta['r'][1][7][1]=0;
		delta['r'][1][7][2]=7;
		delta['r'][2][1][1]=0;
		delta['r'][2][1][2]=-1;
		delta['r'][2][2][1]=0;
		delta['r'][2][2][2]=-2;
		delta['r'][2][3][1]=0;
		delta['r'][2][3][2]=-3;
		delta['r'][2][4][1]=0;
		delta['r'][2][4][2]=-4;
		delta['r'][2][5][1]=0;
		delta['r'][2][5][2]=-5;
		delta['r'][2][6][1]=0;
		delta['r'][2][6][2]=-6;
		delta['r'][2][7][1]=0;
		delta['r'][2][7][2]=-7;
		delta['r'][3][1][1]=1;
		delta['r'][3][1][2]=0;
		delta['r'][3][2][1]=2;
		delta['r'][3][2][2]=0;
		delta['r'][3][3][1]=3;
		delta['r'][3][3][2]=0;
		delta['r'][3][4][1]=4;
		delta['r'][3][4][2]=0;
		delta['r'][3][5][1]=5;
		delta['r'][3][5][2]=0;
		delta['r'][3][6][1]=6;
		delta['r'][3][6][2]=0;
		delta['r'][3][7][1]=7;
		delta['r'][3][7][2]=0;
		delta['r'][4][1][1]=-1;
		delta['r'][4][1][2]=0;
		delta['r'][4][2][1]=-2;
		delta['r'][4][2][2]=0;
		delta['r'][4][3][1]=-3;
		delta['r'][4][3][2]=0;
		delta['r'][4][4][1]=-4;
		delta['r'][4][4][2]=0;
		delta['r'][4][5][1]=-5;
		delta['r'][4][5][2]=0;
		delta['r'][4][6][1]=-6;
		delta['r'][4][6][2]=0;
		delta['r'][4][7][1]=-7;
		delta['r'][4][7][2]=0;
		
		delta['n'][1][1][1]=1;
		delta['n'][1][1][2]=2;
		delta['n'][2][1][1]=2;
		delta['n'][2][1][2]=1;
		delta['n'][3][1][1]=1;
		delta['n'][3][1][2]=-2;
		delta['n'][4][1][1]=2;
		delta['n'][4][1][2]=-1;
		delta['n'][5][1][1]=-1;
		delta['n'][5][1][2]=2;
		delta['n'][6][1][1]=-2;
		delta['n'][6][1][2]=1;
		delta['n'][7][1][1]=-1;
		delta['n'][7][1][2]=-2;
		delta['n'][8][1][1]=-2;
		delta['n'][8][1][2]=-1;
		
		delta['b'][1][1][1]=1;
		delta['b'][1][1][2]=1;
		delta['b'][1][2][1]=2;
		delta['b'][1][2][2]=2;
		delta['b'][1][3][1]=3;
		delta['b'][1][3][2]=3;
		delta['b'][1][4][1]=4;
		delta['b'][1][4][2]=4;
		delta['b'][1][5][1]=5;
		delta['b'][1][5][2]=5;
		delta['b'][1][6][1]=6;
		delta['b'][1][6][2]=6;
		delta['b'][1][7][1]=7;
		delta['b'][1][7][2]=7;
		delta['b'][1][8][1]=8;
		delta['b'][1][8][2]=8;
		delta['b'][2][1][1]=1;
		delta['b'][2][1][2]=-1;
		delta['b'][2][2][1]=2;
		delta['b'][2][2][2]=-2;
		delta['b'][2][3][1]=3;
		delta['b'][2][3][2]=-3;
		delta['b'][2][4][1]=4;
		delta['b'][2][4][2]=-4;
		delta['b'][2][5][1]=5;
		delta['b'][2][5][2]=-5;
		delta['b'][2][6][1]=6;
		delta['b'][2][6][2]=-6;
		delta['b'][2][7][1]=7;
		delta['b'][2][7][2]=-7;
		delta['b'][2][8][1]=8;
		delta['b'][2][8][2]=-8;
		delta['b'][3][1][1]=-1;
		delta['b'][3][1][2]=1;
		delta['b'][3][2][1]=-2;
		delta['b'][3][2][2]=2;
		delta['b'][3][3][1]=-3;
		delta['b'][3][3][2]=3;
		delta['b'][3][4][1]=-4;
		delta['b'][3][4][2]=4;
		delta['b'][3][5][1]=-5;
		delta['b'][3][5][2]=5;
		delta['b'][3][6][1]=-6;
		delta['b'][3][6][2]=6;
		delta['b'][3][7][1]=-7;
		delta['b'][3][7][2]=7;
		delta['b'][3][8][1]=-8;
		delta['b'][3][8][2]=8;
		delta['b'][4][1][1]=-1;
		delta['b'][4][1][2]=-1;
		delta['b'][4][2][1]=-2;
		delta['b'][4][2][2]=-2;
		delta['b'][4][3][1]=-3;
		delta['b'][4][3][2]=-3;
		delta['b'][4][4][1]=-4;
		delta['b'][4][4][2]=-4;
		delta['b'][4][5][1]=-5;
		delta['b'][4][5][2]=-5;
		delta['b'][4][6][1]=-6;
		delta['b'][4][6][2]=-6;
		delta['b'][4][7][1]=-7;
		delta['b'][4][7][2]=-7;
		delta['b'][4][8][1]=-8;
		delta['b'][4][8][2]=-8;
		
		delta['q'][1][1][1]=1;
		delta['q'][1][1][2]=0;
		delta['q'][1][2][1]=2;
		delta['q'][1][2][2]=0;
		delta['q'][1][3][1]=3;
		delta['q'][1][3][2]=0;
		delta['q'][1][4][1]=4;
		delta['q'][1][4][2]=0;
		delta['q'][1][5][1]=5;
		delta['q'][1][5][2]=0;
		delta['q'][1][6][1]=6;
		delta['q'][1][6][2]=0;
		delta['q'][1][7][1]=7;
		delta['q'][1][7][2]=0;
		delta['q'][2][1][1]=1;
		delta['q'][2][1][2]=1;
		delta['q'][2][2][1]=2;
		delta['q'][2][2][2]=2;
		delta['q'][2][3][1]=3;
		delta['q'][2][3][2]=3;
		delta['q'][2][4][1]=4;
		delta['q'][2][4][2]=4;
		delta['q'][2][5][1]=5;
		delta['q'][2][5][2]=5;
		delta['q'][2][6][1]=6;
		delta['q'][2][6][2]=6;
		delta['q'][2][7][1]=7;
		delta['q'][2][7][2]=7;
		delta['q'][3][1][1]=0;
		delta['q'][3][1][2]=1;
		delta['q'][3][2][1]=0;
		delta['q'][3][2][2]=2;
		delta['q'][3][3][1]=0;
		delta['q'][3][3][2]=3;
		delta['q'][3][4][1]=0;
		delta['q'][3][4][2]=4;
		delta['q'][3][5][1]=0;
		delta['q'][3][5][2]=5;
		delta['q'][3][6][1]=0;
		delta['q'][3][6][2]=6;
		delta['q'][3][7][1]=0;
		delta['q'][3][7][2]=7;
		delta['q'][4][1][1]=-1;
		delta['q'][4][1][2]=1;
		delta['q'][4][2][1]=-2;
		delta['q'][4][2][2]=2;
		delta['q'][4][3][1]=-3;
		delta['q'][4][3][2]=3;
		delta['q'][4][4][1]=-4;
		delta['q'][4][4][2]=4;
		delta['q'][4][5][1]=-5;
		delta['q'][4][5][2]=5;
		delta['q'][4][6][1]=-6;
		delta['q'][4][6][2]=6;
		delta['q'][4][7][1]=-7;
		delta['q'][4][7][2]=7;
		delta['q'][5][1][1]=-1;
		delta['q'][5][1][2]=0;
		delta['q'][5][2][1]=-2;
		delta['q'][5][2][2]=0;
		delta['q'][5][3][1]=-3;
		delta['q'][5][3][2]=0;
		delta['q'][5][4][1]=-4;
		delta['q'][5][4][2]=0;
		delta['q'][5][5][1]=-5;
		delta['q'][5][5][2]=0;
		delta['q'][5][6][1]=-6;
		delta['q'][5][6][2]=0;
		delta['q'][5][7][1]=-7;
		delta['q'][5][7][2]=0;
		delta['q'][6][1][1]=-1;
		delta['q'][6][1][2]=-1;
		delta['q'][6][2][1]=-2;
		delta['q'][6][2][2]=-2;
		delta['q'][6][3][1]=-3;
		delta['q'][6][3][2]=-3;
		delta['q'][6][4][1]=-4;
		delta['q'][6][4][2]=-4;
		delta['q'][6][5][1]=-5;
		delta['q'][6][5][2]=-5;
		delta['q'][6][6][1]=-6;
		delta['q'][6][6][2]=-6;
		delta['q'][6][7][1]=-7;
		delta['q'][6][7][2]=-7;
		delta['q'][7][1][1]=0;
		delta['q'][7][1][2]=-1;
		delta['q'][7][2][1]=0;
		delta['q'][7][2][2]=-2;
		delta['q'][7][3][1]=0;
		delta['q'][7][3][2]=-3;
		delta['q'][7][4][1]=0;
		delta['q'][7][4][2]=-4;
		delta['q'][7][5][1]=0;
		delta['q'][7][5][2]=-5;
		delta['q'][7][6][1]=0;
		delta['q'][7][6][2]=-6;
		delta['q'][7][7][1]=0;
		delta['q'][7][7][2]=-7;
		delta['q'][8][1][1]=1;
		delta['q'][8][1][2]=-1;
		delta['q'][8][2][1]=2;
		delta['q'][8][2][2]=-2;
		delta['q'][8][3][1]=3;
		delta['q'][8][3][2]=-3;
		delta['q'][8][4][1]=4;
		delta['q'][8][4][2]=-4;
		delta['q'][8][5][1]=5;
		delta['q'][8][5][2]=-5;
		delta['q'][8][6][1]=6;
		delta['q'][8][6][2]=-6;
		delta['q'][8][7][1]=7;
		delta['q'][8][7][2]=-7;
		
		delta['k'][1][1][1]=1;
		delta['k'][1][1][2]=0;
		delta['k'][2][1][1]=1;
		delta['k'][2][1][2]=1;
		delta['k'][3][1][1]=0;
		delta['k'][3][1][2]=1;
		delta['k'][4][1][1]=-1;
		delta['k'][4][1][2]=1;
		delta['k'][5][1][1]=-1;
		delta['k'][5][1][2]=0;
		delta['k'][6][1][1]=-1;
		delta['k'][6][1][2]=-1;
		delta['k'][7][1][1]=0;
		delta['k'][7][1][2]=-1;
		delta['k'][8][1][1]=1;
		delta['k'][8][1][2]=-1;
		delta['k'][9][1][1]=0;
		delta['k'][9][1][2]=2;
		delta['k'][10][1][1]=0;
		delta['k'][10][1][2]=-2;
		
		delta['p'][1][1][1]=1;
		delta['p'][1][1][2]=0;
		delta['p'][1][2][1]=2;
		delta['p'][1][2][2]=0;
		delta['p'][2][1][1]=1;
		delta['p'][2][1][2]=1;
		delta['p'][3][1][1]=1;
		delta['p'][3][1][2]=-1;
		
		delta['t'][1][1][1]=0;
		delta['t'][1][1][2]=1;
		delta['t'][1][2][1]=0;
		delta['t'][1][2][2]=2;
		delta['t'][1][3][1]=0;
		delta['t'][1][3][2]=3;
		delta['t'][1][4][1]=0;
		delta['t'][1][4][2]=4;
		delta['t'][1][5][1]=0;
		delta['t'][1][5][2]=5;
		delta['t'][1][6][1]=0;
		delta['t'][1][6][2]=6;
		delta['t'][1][7][1]=0;
		delta['t'][1][7][2]=7;
		delta['t'][2][1][1]=0;
		delta['t'][2][1][2]=-1;
		delta['t'][2][2][1]=0;
		delta['t'][2][2][2]=-2;
		delta['t'][2][3][1]=0;
		delta['t'][2][3][2]=-3;
		delta['t'][2][4][1]=0;
		delta['t'][2][4][2]=-4;
		delta['t'][2][5][1]=0;
		delta['t'][2][5][2]=-5;
		delta['t'][2][6][1]=0;
		delta['t'][2][6][2]=-6;
		delta['t'][2][7][1]=0;
		delta['t'][2][7][2]=-7;
		delta['t'][3][1][1]=1;
		delta['t'][3][1][2]=0;
		delta['t'][3][2][1]=2;
		delta['t'][3][2][2]=0;
		delta['t'][3][3][1]=3;
		delta['t'][3][3][2]=0;
		delta['t'][3][4][1]=4;
		delta['t'][3][4][2]=0;
		delta['t'][3][5][1]=5;
		delta['t'][3][5][2]=0;
		delta['t'][3][6][1]=6;
		delta['t'][3][6][2]=0;
		delta['t'][3][7][1]=7;
		delta['t'][3][7][2]=0;
		delta['t'][4][1][1]=-1;
		delta['t'][4][1][2]=0;
		delta['t'][4][2][1]=-2;
		delta['t'][4][2][2]=0;
		delta['t'][4][3][1]=-3;
		delta['t'][4][3][2]=0;
		delta['t'][4][4][1]=-4;
		delta['t'][4][4][2]=0;
		delta['t'][4][5][1]=-5;
		delta['t'][4][5][2]=0;
		delta['t'][4][6][1]=-6;
		delta['t'][4][6][2]=0;
		delta['t'][4][7][1]=-7;
		delta['t'][4][7][2]=0;
		
		delta['m'][1][1][1]=1;
		delta['m'][1][1][2]=2;
		delta['m'][2][1][1]=2;
		delta['m'][2][1][2]=1;
		delta['m'][3][1][1]=1;
		delta['m'][3][1][2]=-2;
		delta['m'][4][1][1]=2;
		delta['m'][4][1][2]=-1;
		delta['m'][5][1][1]=-1;
		delta['m'][5][1][2]=2;
		delta['m'][6][1][1]=-2;
		delta['m'][6][1][2]=1;
		delta['m'][7][1][1]=-1;
		delta['m'][7][1][2]=-2;
		delta['m'][8][1][1]=-2;
		delta['m'][8][1][2]=-1;
		
		delta['v'][1][1][1]=1;
		delta['v'][1][1][2]=1;
		delta['v'][1][2][1]=2;
		delta['v'][1][2][2]=2;
		delta['v'][1][3][1]=3;
		delta['v'][1][3][2]=3;
		delta['v'][1][4][1]=4;
		delta['v'][1][4][2]=4;
		delta['v'][1][5][1]=5;
		delta['v'][1][5][2]=5;
		delta['v'][1][6][1]=6;
		delta['v'][1][6][2]=6;
		delta['v'][1][7][1]=7;
		delta['v'][1][7][2]=7;
		delta['v'][1][8][1]=8;
		delta['v'][1][8][2]=8;
		delta['v'][2][1][1]=1;
		delta['v'][2][1][2]=-1;
		delta['v'][2][2][1]=2;
		delta['v'][2][2][2]=-2;
		delta['v'][2][3][1]=3;
		delta['v'][2][3][2]=-3;
		delta['v'][2][4][1]=4;
		delta['v'][2][4][2]=-4;
		delta['v'][2][5][1]=5;
		delta['v'][2][5][2]=-5;
		delta['v'][2][6][1]=6;
		delta['v'][2][6][2]=-6;
		delta['v'][2][7][1]=7;
		delta['v'][2][7][2]=-7;
		delta['v'][2][8][1]=8;
		delta['v'][2][8][2]=-8;
		delta['v'][3][1][1]=-1;
		delta['v'][3][1][2]=1;
		delta['v'][3][2][1]=-2;
		delta['v'][3][2][2]=2;
		delta['v'][3][3][1]=-3;
		delta['v'][3][3][2]=3;
		delta['v'][3][4][1]=-4;
		delta['v'][3][4][2]=4;
		delta['v'][3][5][1]=-5;
		delta['v'][3][5][2]=5;
		delta['v'][3][6][1]=-6;
		delta['v'][3][6][2]=6;
		delta['v'][3][7][1]=-7;
		delta['v'][3][7][2]=7;
		delta['v'][3][8][1]=-8;
		delta['v'][3][8][2]=8;
		delta['v'][4][1][1]=-1;
		delta['v'][4][1][2]=-1;
		delta['v'][4][2][1]=-2;
		delta['v'][4][2][2]=-2;
		delta['v'][4][3][1]=-3;
		delta['v'][4][3][2]=-3;
		delta['v'][4][4][1]=-4;
		delta['v'][4][4][2]=-4;
		delta['v'][4][5][1]=-5;
		delta['v'][4][5][2]=-5;
		delta['v'][4][6][1]=-6;
		delta['v'][4][6][2]=-6;
		delta['v'][4][7][1]=-7;
		delta['v'][4][7][2]=-7;
		delta['v'][4][8][1]=-8;
		delta['v'][4][8][2]=-8;
		
		delta['w'][1][1][1]=1;
		delta['w'][1][1][2]=0;
		delta['w'][1][2][1]=2;
		delta['w'][1][2][2]=0;
		delta['w'][1][3][1]=3;
		delta['w'][1][3][2]=0;
		delta['w'][1][4][1]=4;
		delta['w'][1][4][2]=0;
		delta['w'][1][5][1]=5;
		delta['w'][1][5][2]=0;
		delta['w'][1][6][1]=6;
		delta['w'][1][6][2]=0;
		delta['w'][1][7][1]=7;
		delta['w'][1][7][2]=0;
		delta['w'][2][1][1]=1;
		delta['w'][2][1][2]=1;
		delta['w'][2][2][1]=2;
		delta['w'][2][2][2]=2;
		delta['w'][2][3][1]=3;
		delta['w'][2][3][2]=3;
		delta['w'][2][4][1]=4;
		delta['w'][2][4][2]=4;
		delta['w'][2][5][1]=5;
		delta['w'][2][5][2]=5;
		delta['w'][2][6][1]=6;
		delta['w'][2][6][2]=6;
		delta['w'][2][7][1]=7;
		delta['w'][2][7][2]=7;
		delta['w'][3][1][1]=0;
		delta['w'][3][1][2]=1;
		delta['w'][3][2][1]=0;
		delta['w'][3][2][2]=2;
		delta['w'][3][3][1]=0;
		delta['w'][3][3][2]=3;
		delta['w'][3][4][1]=0;
		delta['w'][3][4][2]=4;
		delta['w'][3][5][1]=0;
		delta['w'][3][5][2]=5;
		delta['w'][3][6][1]=0;
		delta['w'][3][6][2]=6;
		delta['w'][3][7][1]=0;
		delta['w'][3][7][2]=7;
		delta['w'][4][1][1]=-1;
		delta['w'][4][1][2]=1;
		delta['w'][4][2][1]=-2;
		delta['w'][4][2][2]=2;
		delta['w'][4][3][1]=-3;
		delta['w'][4][3][2]=3;
		delta['w'][4][4][1]=-4;
		delta['w'][4][4][2]=4;
		delta['w'][4][5][1]=-5;
		delta['w'][4][5][2]=5;
		delta['w'][4][6][1]=-6;
		delta['w'][4][6][2]=6;
		delta['w'][4][7][1]=-7;
		delta['w'][4][7][2]=7;
		delta['w'][5][1][1]=-1;
		delta['w'][5][1][2]=0;
		delta['w'][5][2][1]=-2;
		delta['w'][5][2][2]=0;
		delta['w'][5][3][1]=-3;
		delta['w'][5][3][2]=0;
		delta['w'][5][4][1]=-4;
		delta['w'][5][4][2]=0;
		delta['w'][5][5][1]=-5;
		delta['w'][5][5][2]=0;
		delta['w'][5][6][1]=-6;
		delta['w'][5][6][2]=0;
		delta['w'][5][7][1]=-7;
		delta['w'][5][7][2]=0;
		delta['w'][6][1][1]=-1;
		delta['w'][6][1][2]=-1;
		delta['w'][6][2][1]=-2;
		delta['w'][6][2][2]=-2;
		delta['w'][6][3][1]=-3;
		delta['w'][6][3][2]=-3;
		delta['w'][6][4][1]=-4;
		delta['w'][6][4][2]=-4;
		delta['w'][6][5][1]=-5;
		delta['w'][6][5][2]=-5;
		delta['w'][6][6][1]=-6;
		delta['w'][6][6][2]=-6;
		delta['w'][6][7][1]=-7;
		delta['w'][6][7][2]=-7;
		delta['w'][7][1][1]=0;
		delta['w'][7][1][2]=-1;
		delta['w'][7][2][1]=0;
		delta['w'][7][2][2]=-2;
		delta['w'][7][3][1]=0;
		delta['w'][7][3][2]=-3;
		delta['w'][7][4][1]=0;
		delta['w'][7][4][2]=-4;
		delta['w'][7][5][1]=0;
		delta['w'][7][5][2]=-5;
		delta['w'][7][6][1]=0;
		delta['w'][7][6][2]=-6;
		delta['w'][7][7][1]=0;
		delta['w'][7][7][2]=-7;
		delta['w'][8][1][1]=1;
		delta['w'][8][1][2]=-1;
		delta['w'][8][2][1]=2;
		delta['w'][8][2][2]=-2;
		delta['w'][8][3][1]=3;
		delta['w'][8][3][2]=-3;
		delta['w'][8][4][1]=4;
		delta['w'][8][4][2]=-4;
		delta['w'][8][5][1]=5;
		delta['w'][8][5][2]=-5;
		delta['w'][8][6][1]=6;
		delta['w'][8][6][2]=-6;
		delta['w'][8][7][1]=7;
		delta['w'][8][7][2]=-7;
		
		delta['l'][1][1][1]=1;
		delta['l'][1][1][2]=0;
		delta['l'][2][1][1]=1;
		delta['l'][2][1][2]=1;
		delta['l'][3][1][1]=0;
		delta['l'][3][1][2]=1;
		delta['l'][4][1][1]=-1;
		delta['l'][4][1][2]=1;
		delta['l'][5][1][1]=-1;
		delta['l'][5][1][2]=0;
		delta['l'][6][1][1]=-1;
		delta['l'][6][1][2]=-1;
		delta['l'][7][1][1]=0;
		delta['l'][7][1][2]=-1;
		delta['l'][8][1][1]=1;
		delta['l'][8][1][2]=-1;
		delta['l'][9][1][1]=0;
		delta['l'][9][1][2]=2;
		delta['l'][10][1][1]=0;
		delta['l'][10][1][2]=-2;
		
		delta['o'][1][1][1]=-1;
		delta['o'][1][1][2]=0;
		delta['o'][1][2][1]=-2;
		delta['o'][1][2][2]=0;
		delta['o'][2][1][1]=-1;
		delta['o'][2][1][2]=1;
		delta['o'][3][1][1]=-1;
		delta['o'][3][1][2]=-1;
		
		boardButton[1][1]=(Button)findViewById(R.id.board_1a);
		boardButton[1][2]=(Button)findViewById(R.id.board_1b);
		boardButton[1][3]=(Button)findViewById(R.id.board_1c);
		boardButton[1][4]=(Button)findViewById(R.id.board_1d);
		boardButton[1][5]=(Button)findViewById(R.id.board_1e);
		boardButton[1][6]=(Button)findViewById(R.id.board_1f);
		boardButton[1][7]=(Button)findViewById(R.id.board_1g);
		boardButton[1][8]=(Button)findViewById(R.id.board_1h);
		boardButton[2][1]=(Button)findViewById(R.id.board_2a);
		boardButton[2][2]=(Button)findViewById(R.id.board_2b);
		boardButton[2][3]=(Button)findViewById(R.id.board_2c);
		boardButton[2][4]=(Button)findViewById(R.id.board_2d);
		boardButton[2][5]=(Button)findViewById(R.id.board_2e);
		boardButton[2][6]=(Button)findViewById(R.id.board_2f);
		boardButton[2][7]=(Button)findViewById(R.id.board_2g);
		boardButton[2][8]=(Button)findViewById(R.id.board_2h);
		boardButton[3][1]=(Button)findViewById(R.id.board_3a);
		boardButton[3][2]=(Button)findViewById(R.id.board_3b);
		boardButton[3][3]=(Button)findViewById(R.id.board_3c);
		boardButton[3][4]=(Button)findViewById(R.id.board_3d);
		boardButton[3][5]=(Button)findViewById(R.id.board_3e);
		boardButton[3][6]=(Button)findViewById(R.id.board_3f);
		boardButton[3][7]=(Button)findViewById(R.id.board_3g);
		boardButton[3][8]=(Button)findViewById(R.id.board_3h);
		boardButton[4][1]=(Button)findViewById(R.id.board_4a);
		boardButton[4][2]=(Button)findViewById(R.id.board_4b);
		boardButton[4][3]=(Button)findViewById(R.id.board_4c);
		boardButton[4][4]=(Button)findViewById(R.id.board_4d);
		boardButton[4][5]=(Button)findViewById(R.id.board_4e);
		boardButton[4][6]=(Button)findViewById(R.id.board_4f);
		boardButton[4][7]=(Button)findViewById(R.id.board_4g);
		boardButton[4][8]=(Button)findViewById(R.id.board_4h);
		boardButton[5][1]=(Button)findViewById(R.id.board_5a);
		boardButton[5][2]=(Button)findViewById(R.id.board_5b);
		boardButton[5][3]=(Button)findViewById(R.id.board_5c);
		boardButton[5][4]=(Button)findViewById(R.id.board_5d);
		boardButton[5][5]=(Button)findViewById(R.id.board_5e);
		boardButton[5][6]=(Button)findViewById(R.id.board_5f);
		boardButton[5][7]=(Button)findViewById(R.id.board_5g);
		boardButton[5][8]=(Button)findViewById(R.id.board_5h);
		boardButton[6][1]=(Button)findViewById(R.id.board_6a);
		boardButton[6][2]=(Button)findViewById(R.id.board_6b);
		boardButton[6][3]=(Button)findViewById(R.id.board_6c);
		boardButton[6][4]=(Button)findViewById(R.id.board_6d);
		boardButton[6][5]=(Button)findViewById(R.id.board_6e);
		boardButton[6][6]=(Button)findViewById(R.id.board_6f);
		boardButton[6][7]=(Button)findViewById(R.id.board_6g);
		boardButton[6][8]=(Button)findViewById(R.id.board_6h);
		boardButton[7][1]=(Button)findViewById(R.id.board_7a);
		boardButton[7][2]=(Button)findViewById(R.id.board_7b);
		boardButton[7][3]=(Button)findViewById(R.id.board_7c);
		boardButton[7][4]=(Button)findViewById(R.id.board_7d);
		boardButton[7][5]=(Button)findViewById(R.id.board_7e);
		boardButton[7][6]=(Button)findViewById(R.id.board_7f);
		boardButton[7][7]=(Button)findViewById(R.id.board_7g);
		boardButton[7][8]=(Button)findViewById(R.id.board_7h);
		boardButton[8][1]=(Button)findViewById(R.id.board_8a);
		boardButton[8][2]=(Button)findViewById(R.id.board_8b);
		boardButton[8][3]=(Button)findViewById(R.id.board_8c);
		boardButton[8][4]=(Button)findViewById(R.id.board_8d);
		boardButton[8][5]=(Button)findViewById(R.id.board_8e);
		boardButton[8][6]=(Button)findViewById(R.id.board_8f);
		boardButton[8][7]=(Button)findViewById(R.id.board_8g);
		boardButton[8][8]=(Button)findViewById(R.id.board_8h);
		
		typeface=Typeface.createFromAsset(getAssets(),"fonts/Chess-7.TTF");
		for(int i=1;i<=8;++i)
		{
			for(int j=1;j<=8;++j)
			{
				boardButton[i][j].setTypeface(typeface);
				boardButton[i][j].setOnClickListener(mBoardClick);
			}
		}
		updateDisplay();
	}
	
	public void onDestroy()
	{
		super.onDestroy();
		Toast.makeText(this,R.string.quit_end,Toast.LENGTH_SHORT).show();
	}
	
	public boolean onKeyDown(int keyCode,KeyEvent event)
	{
		switch(keyCode)
		{
		case KeyEvent.KEYCODE_BACK:
			if(!isExiting)
			{
				Toast.makeText(this,R.string.quit_ing,Toast.LENGTH_SHORT).show();
				isExiting=true;
				new Handler().postDelayed(new Runnable()
				{
					public void run()
					{
						isExiting=false;
					}
				},3000);
			}
			else finish();
			return true;
		default:
			return super.onKeyDown(keyCode,event);
		}
	}
	
	public boolean onCreateOptionsMenu(Menu menu)
	{
		getMenuInflater().inflate(R.menu.kennychess,menu);
		return true;
	}
	
	public boolean onOptionsItemSelected(MenuItem item)
	{
		super.onOptionsItemSelected(item);
		switch(item.getItemId())
		{
		case R.id.action_information:
			((TextView)new AlertDialog.Builder(this).setIcon(R.drawable.ic_launcher).setTitle(R.string.full_name).setMessage(R.string.information).setPositiveButton(android.R.string.ok,null).show().findViewById(android.R.id.message)).setMovementMethod(LinkMovementMethod.getInstance());
			return true;
		case R.id.action_settings:
			startActivity(new Intent(this,Settings.class));
			return true;
		case R.id.action_exit:
			finish();
			return true;
		}
		return false;
	}
	
	void updateDisplay()
	{
		for(int i=1;i<=8;++i)
		{
			for(int j=1;j<=8;++j)
			{
				if((i+j)%2==0)boardButton[i][j].setBackgroundColor(color[0]);
				if((i+j)%2==1)boardButton[i][j].setBackgroundColor(color[1]);
				boardButton[i][j].setText(""+boardChar[i][j]);
			}
		}
		((TextView)findViewById(R.id.text)).setText(String.format(getString(R.string.nowTurn),nowTurn));
		if(isPlayer1Checkmated())
		{
			new AlertDialog.Builder(this).setTitle(getString(R.string.end_2p)).setPositiveButton(android.R.string.ok,new DialogInterface.OnClickListener()
			{
				public void onClick(DialogInterface dialog, int which)
				{
					finish();
				}
			}).setCancelable(false).show();
		}
		if(isPlayer2Checkmated())
		{
			new AlertDialog.Builder(this).setTitle(getString(R.string.end_1p)).setPositiveButton(android.R.string.ok,new DialogInterface.OnClickListener()
			{
				public void onClick(DialogInterface dialog, int which)
				{
					finish();
				}
			}).setCancelable(false).show();
		}
		if(isPlayer1Stalemated()||isPlayer2Stalemated())
		{
			new AlertDialog.Builder(this).setTitle(getString(R.string.end_draw)).setPositiveButton(android.R.string.ok,new DialogInterface.OnClickListener()
			{
				public void onClick(DialogInterface dialog, int which)
				{
					finish();
				}
			}).setCancelable(false).show();
		}
	}
	
	boolean isPlayer1Checked()
	{
		boolean isChecked=false;
		for(int r=1;r<=8;++r)
		{
			for(int c=1;c<=8;++c)
			{
				if(boardChar[r][c]==' '||boardChar[r][c]=='r'||boardChar[r][c]=='n'||boardChar[r][c]=='b'||boardChar[r][c]=='q'||boardChar[r][c]=='k'||boardChar[r][c]=='p')continue;
				for(int i=1;i<=10;++i)
				{
					if(delta[boardChar[r][c]][i][1][1]==0&&delta[boardChar[r][c]][i][1][2]==0)break;
					for(int j=1;j<=7;++j)
					{
						if(delta[boardChar[r][c]][i][j][1]==0&&delta[boardChar[r][c]][i][j][2]==0)break;
						if(r+delta[boardChar[r][c]][i][j][1]<1||r+delta[boardChar[r][c]][i][j][1]>8||c+delta[boardChar[r][c]][i][j][2]<1||c+delta[boardChar[r][c]][i][j][2]>8)break;
						if(boardChar[r][c]=='o')
						{
							if(r!=7&&i==1&&j==2)break;
							if(i==1&&boardChar[r+delta[boardChar[r][c]][i][j][1]][c+delta[boardChar[r][c]][i][j][2]]!=' ')break;
							if(i>=2&&boardChar[r+delta[boardChar[r][c]][i][j][1]][c+delta[boardChar[r][c]][i][j][2]]==' ')break;
						}
						if(boardChar[r][c]=='l'&&i>=9)break;
						if(boardChar[r+delta[boardChar[r][c]][i][j][1]][c+delta[boardChar[r][c]][i][j][2]]=='k')isChecked=true;
						if(boardChar[r+delta[boardChar[r][c]][i][j][1]][c+delta[boardChar[r][c]][i][j][2]]!=' ')break;
					}
				}
			}
		}
		return isChecked;
	}
	
	boolean isPlayer2Checked()
	{
		boolean isChecked=false;
		for(int r=1;r<=8;++r)
		{
			for(int c=1;c<=8;++c)
			{
				if(boardChar[r][c]==' '||boardChar[r][c]=='t'||boardChar[r][c]=='m'||boardChar[r][c]=='w'||boardChar[r][c]=='v'||boardChar[r][c]=='l'||boardChar[r][c]=='o')continue;
				for(int i=1;i<=10;++i)
				{
					if(delta[boardChar[r][c]][i][1][1]==0&&delta[boardChar[r][c]][i][1][2]==0)break;
					for(int j=1;j<=7;++j)
					{
						if(delta[boardChar[r][c]][i][j][1]==0&&delta[boardChar[r][c]][i][j][2]==0)break;
						if(r+delta[boardChar[r][c]][i][j][1]<1||r+delta[boardChar[r][c]][i][j][1]>8||c+delta[boardChar[r][c]][i][j][2]<1||c+delta[boardChar[r][c]][i][j][2]>8)break;
						if(boardChar[r][c]=='p')
						{
							if(r!=2&&i==1&&j==2)break;
							if(i==1&&boardChar[r+delta[boardChar[r][c]][i][j][1]][c+delta[boardChar[r][c]][i][j][2]]!=' ')break;
							if(i>=2&&boardChar[r+delta[boardChar[r][c]][i][j][1]][c+delta[boardChar[r][c]][i][j][2]]==' ')break;
						}
						if(boardChar[r][c]=='k'&&i>=9)break;
						if(boardChar[r+delta[boardChar[r][c]][i][j][1]][c+delta[boardChar[r][c]][i][j][2]]=='l')isChecked=true;
						if(boardChar[r+delta[boardChar[r][c]][i][j][1]][c+delta[boardChar[r][c]][i][j][2]]!=' ')break;
					}
				}
			}
		}
		return isChecked;
	}
	
	boolean isPlayer1Suiciding(int r,int c,int dr,int dc)
	{
		char boardCharS=boardChar[r][c],boardCharE=boardChar[r+dr][c+dc];
		boolean isSuiciding=false;
		boardChar[r][c]=' ';
		boardChar[r+dr][c+dc]=boardCharS;
		isSuiciding=isPlayer1Checked();
		boardChar[r][c]=boardCharS;
		boardChar[r+dr][c+dc]=boardCharE;
		return isSuiciding;
	}
	
	boolean isPlayer2Suiciding(int r,int c,int dr,int dc)
	{
		char boardCharS=boardChar[r][c],boardCharE=boardChar[r+dr][c+dc];
		boolean isSuiciding=false;
		boardChar[r][c]=' ';
		boardChar[r+dr][c+dc]=boardCharS;
		isSuiciding=isPlayer2Checked();
		boardChar[r][c]=boardCharS;
		boardChar[r+dr][c+dc]=boardCharE;
		return isSuiciding;
	}
	
	boolean isPlayer1Checkmated()
	{
		boolean isCheckmated=true;
		if(!isPlayer1Checked())return false;
		for(int r=1;r<=8;++r)
		{
			for(int c=1;c<=8;++c)
			{
				if(boardChar[r][c]==' '||boardChar[r][c]=='t'||boardChar[r][c]=='m'||boardChar[r][c]=='w'||boardChar[r][c]=='v'||boardChar[r][c]=='l'||boardChar[r][c]=='o')continue;
				for(int i=1;i<=10;++i)
				{
					if(delta[boardChar[r][c]][i][1][1]==0&&delta[boardChar[r][c]][i][1][2]==0)break;
					for(int j=1;j<=7;++j)
					{
						if(delta[boardChar[r][c]][i][j][1]==0&&delta[boardChar[r][c]][i][j][2]==0)break;
						if(r+delta[boardChar[r][c]][i][j][1]<1||r+delta[boardChar[r][c]][i][j][1]>8||c+delta[boardChar[r][c]][i][j][2]<1||c+delta[boardChar[r][c]][i][j][2]>8)break;
						if(boardChar[r][c]=='p')
						{
							if(i==1)
							{
								if(boardChar[r+delta[boardChar[r][c]][i][j][1]][c+delta[boardChar[r][c]][i][j][2]]!=' ')break;
								if(j==2&&r!=2)break;
							}
							if(i>=2&&boardChar[r+delta[boardChar[r][c]][i][j][1]][c+delta[boardChar[r][c]][i][j][2]]==' ')break;
						}
						if(boardChar[r][c]=='k'&&i>=9)break;
						if(boardChar[r+delta[boardChar[r][c]][i][j][1]][c+delta[boardChar[r][c]][i][j][2]]=='r'||boardChar[r+delta[boardChar[r][c]][i][j][1]][c+delta[boardChar[r][c]][i][j][2]]=='n'||boardChar[r+delta[boardChar[r][c]][i][j][1]][c+delta[boardChar[r][c]][i][j][2]]=='b'||boardChar[r+delta[boardChar[r][c]][i][j][1]][c+delta[boardChar[r][c]][i][j][2]]=='q'||boardChar[r+delta[boardChar[r][c]][i][j][1]][c+delta[boardChar[r][c]][i][j][2]]=='k'||boardChar[r+delta[boardChar[r][c]][i][j][1]][c+delta[boardChar[r][c]][i][j][2]]=='p')break;
						if(isPlayer1Suiciding(r,c,delta[boardChar[r][c]][i][j][1],delta[boardChar[r][c]][i][j][2]))continue;
						isCheckmated=false;
						break;
					}
				}
			}
		}
		return isCheckmated;
	}
	
	boolean isPlayer2Checkmated()
	{
		boolean isCheckmated=true;
		if(!isPlayer2Checked())return false;
		for(int r=1;r<=8;++r)
		{
			for(int c=1;c<=8;++c)
			{
				if(boardChar[r][c]==' '||boardChar[r][c]=='r'||boardChar[r][c]=='n'||boardChar[r][c]=='b'||boardChar[r][c]=='q'||boardChar[r][c]=='k'||boardChar[r][c]=='p')continue;
				for(int i=1;i<=10;++i)
				{
					if(delta[boardChar[r][c]][i][1][1]==0&&delta[boardChar[r][c]][i][1][2]==0)break;
					for(int j=1;j<=7;++j)
					{
						if(delta[boardChar[r][c]][i][j][1]==0&&delta[boardChar[r][c]][i][j][2]==0)break;
						if(r+delta[boardChar[r][c]][i][j][1]<1||r+delta[boardChar[r][c]][i][j][1]>8||c+delta[boardChar[r][c]][i][j][2]<1||c+delta[boardChar[r][c]][i][j][2]>8)break;
						if(boardChar[r][c]=='o')
						{
							if(i==1)
							{
								if(boardChar[r+delta[boardChar[r][c]][i][j][1]][c+delta[boardChar[r][c]][i][j][2]]!=' ')break;
								if(j==2&&r!=2)break;
							}
							if(i>=2&&boardChar[r+delta[boardChar[r][c]][i][j][1]][c+delta[boardChar[r][c]][i][j][2]]==' ')break;
						}
						if(boardChar[r][c]=='l'&&i>=9)break;
						if(boardChar[r+delta[boardChar[r][c]][i][j][1]][c+delta[boardChar[r][c]][i][j][2]]=='t'||boardChar[r+delta[boardChar[r][c]][i][j][1]][c+delta[boardChar[r][c]][i][j][2]]=='m'||boardChar[r+delta[boardChar[r][c]][i][j][1]][c+delta[boardChar[r][c]][i][j][2]]=='w'||boardChar[r+delta[boardChar[r][c]][i][j][1]][c+delta[boardChar[r][c]][i][j][2]]=='v'||boardChar[r+delta[boardChar[r][c]][i][j][1]][c+delta[boardChar[r][c]][i][j][2]]=='l'||boardChar[r+delta[boardChar[r][c]][i][j][1]][c+delta[boardChar[r][c]][i][j][2]]=='o')break;
						if(isPlayer2Suiciding(r,c,delta[boardChar[r][c]][i][j][1],delta[boardChar[r][c]][i][j][2]))continue;
						isCheckmated=false;
						break;
					}
				}
			}
		}
		return isCheckmated;
	}
	
	boolean isPlayer1Stalemated()
	{
		boolean isStalemated=true;
		if(isPlayer1Checked())return false;
		for(int r=1;r<=8;++r)
		{
			for(int c=1;c<=8;++c)
			{
				if(boardChar[r][c]==' '||boardChar[r][c]=='t'||boardChar[r][c]=='m'||boardChar[r][c]=='w'||boardChar[r][c]=='v'||boardChar[r][c]=='l'||boardChar[r][c]=='o')continue;
				for(int i=1;i<=10;++i)
				{
					if(delta[boardChar[r][c]][i][1][1]==0&&delta[boardChar[r][c]][i][1][2]==0)break;
					for(int j=1;j<=7;++j)
					{
						if(delta[boardChar[r][c]][i][j][1]==0&&delta[boardChar[r][c]][i][j][2]==0)break;
						if(r+delta[boardChar[r][c]][i][j][1]<1||r+delta[boardChar[r][c]][i][j][1]>8||c+delta[boardChar[r][c]][i][j][2]<1||c+delta[boardChar[r][c]][i][j][2]>8)break;
						if(boardChar[r][c]=='p')
						{
							if(i==1)
							{
								if(boardChar[r+delta[boardChar[r][c]][i][j][1]][c+delta[boardChar[r][c]][i][j][2]]!=' ')break;
								if(j==2&&r!=2)break;
							}
							if(i>=2&&boardChar[r+delta[boardChar[r][c]][i][j][1]][c+delta[boardChar[r][c]][i][j][2]]==' ')break;
						}
						if(boardChar[r][c]=='k'&&i>=9)break;
						if(boardChar[r+delta[boardChar[r][c]][i][j][1]][c+delta[boardChar[r][c]][i][j][2]]=='r'||boardChar[r+delta[boardChar[r][c]][i][j][1]][c+delta[boardChar[r][c]][i][j][2]]=='n'||boardChar[r+delta[boardChar[r][c]][i][j][1]][c+delta[boardChar[r][c]][i][j][2]]=='b'||boardChar[r+delta[boardChar[r][c]][i][j][1]][c+delta[boardChar[r][c]][i][j][2]]=='q'||boardChar[r+delta[boardChar[r][c]][i][j][1]][c+delta[boardChar[r][c]][i][j][2]]=='k'||boardChar[r+delta[boardChar[r][c]][i][j][1]][c+delta[boardChar[r][c]][i][j][2]]=='p')break;
						if(isPlayer1Suiciding(r,c,delta[boardChar[r][c]][i][j][1],delta[boardChar[r][c]][i][j][2]))continue;
						isStalemated=false;
						break;
					}
				}
			}
		}
		return isStalemated;
	}
	
	boolean isPlayer2Stalemated()
	{
		boolean isStalemated=true;
		if(isPlayer2Checked())return false;
		for(int r=1;r<=8;++r)
		{
			for(int c=1;c<=8;++c)
			{
				if(boardChar[r][c]==' '||boardChar[r][c]=='r'||boardChar[r][c]=='n'||boardChar[r][c]=='b'||boardChar[r][c]=='q'||boardChar[r][c]=='k'||boardChar[r][c]=='p')continue;
				for(int i=1;i<=10;++i)
				{
					if(delta[boardChar[r][c]][i][1][1]==0&&delta[boardChar[r][c]][i][1][2]==0)break;
					for(int j=1;j<=7;++j)
					{
						if(delta[boardChar[r][c]][i][j][1]==0&&delta[boardChar[r][c]][i][j][2]==0)break;
						if(r+delta[boardChar[r][c]][i][j][1]<1||r+delta[boardChar[r][c]][i][j][1]>8||c+delta[boardChar[r][c]][i][j][2]<1||c+delta[boardChar[r][c]][i][j][2]>8)break;
						if(boardChar[r][c]=='o')
						{
							if(i==1)
							{
								if(boardChar[r+delta[boardChar[r][c]][i][j][1]][c+delta[boardChar[r][c]][i][j][2]]!=' ')break;
								if(j==2&&r!=2)break;
							}
							if(i>=2&&boardChar[r+delta[boardChar[r][c]][i][j][1]][c+delta[boardChar[r][c]][i][j][2]]==' ')break;
						}
						if(boardChar[r][c]=='l'&&i>=9)break;
						if(boardChar[r+delta[boardChar[r][c]][i][j][1]][c+delta[boardChar[r][c]][i][j][2]]=='t'||boardChar[r+delta[boardChar[r][c]][i][j][1]][c+delta[boardChar[r][c]][i][j][2]]=='m'||boardChar[r+delta[boardChar[r][c]][i][j][1]][c+delta[boardChar[r][c]][i][j][2]]=='w'||boardChar[r+delta[boardChar[r][c]][i][j][1]][c+delta[boardChar[r][c]][i][j][2]]=='v'||boardChar[r+delta[boardChar[r][c]][i][j][1]][c+delta[boardChar[r][c]][i][j][2]]=='l'||boardChar[r+delta[boardChar[r][c]][i][j][1]][c+delta[boardChar[r][c]][i][j][2]]=='o')break;
						if(isPlayer2Suiciding(r,c,delta[boardChar[r][c]][i][j][1],delta[boardChar[r][c]][i][j][2]))continue;
						isStalemated=false;
						break;
					}
				}
			}
		}
		return isStalemated;
	}
	
	Button.OnClickListener mBoardClick=new Button.OnClickListener()
	{
		int r,c,mSelect;
		public void onClick(View v)
		{
			boolean canMove,canCastling,breakFlag;
			int moveType;
			for(int i=1;i<=8;++i)
			{
				for(int j=1;j<=8;++j)
				{
					if(boardButton[i][j].getId()==v.getId())
					{
						r=i;
						c=j;
						break;
					}
				}
			}
			if(!isFocused)
			{
				if(boardChar[r][c]==' ')return;
				if(nowTurn==1&&(boardChar[r][c]=='t'||boardChar[r][c]=='m'||boardChar[r][c]=='w'||boardChar[r][c]=='v'||boardChar[r][c]=='l'||boardChar[r][c]=='o'))return;
				if(nowTurn==2&&(boardChar[r][c]=='r'||boardChar[r][c]=='n'||boardChar[r][c]=='b'||boardChar[r][c]=='q'||boardChar[r][c]=='k'||boardChar[r][c]=='p'))return;
				for(int i=1;i<=8;++i)for(int j=1;j<=8;++j)isMovable[i][j]=false;
				canMove=false;
				for(int i=1;i<=10;++i)
				{
					if(delta[boardChar[r][c]][i][1][1]==0&&delta[boardChar[r][c]][i][1][2]==0)break;
					for(int j=1;j<=7;++j)
					{
						if(delta[boardChar[r][c]][i][j][1]==0&&delta[boardChar[r][c]][i][j][2]==0)break;
						if(r+delta[boardChar[r][c]][i][j][1]<1||r+delta[boardChar[r][c]][i][j][1]>8||c+delta[boardChar[r][c]][i][j][2]<1||c+delta[boardChar[r][c]][i][j][2]>8)break;
						breakFlag=false;
						moveType=3;
						if(boardChar[r][c]=='p')
						{
							if(i==1)
							{
								if(boardChar[r+delta[boardChar[r][c]][i][j][1]][c+delta[boardChar[r][c]][i][j][2]]!=' ')break;
								if(j==2&&r!=2)break;
							}
							if(i>=2)
							{
								if(isEnpassantable&&r==5&&c==prvC-delta[boardChar[r][c]][i][j][2])moveType=4;
								else if(boardChar[r+delta[boardChar[r][c]][i][j][1]][c+delta[boardChar[r][c]][i][j][2]]==' ')break;
							}
						}
						if(boardChar[r][c]=='o')
						{
							if(i==1)
							{
								if(boardChar[r+delta[boardChar[r][c]][i][j][1]][c+delta[boardChar[r][c]][i][j][2]]!=' ')break;
								if(j==2&&r!=7)break;
							}
							if(i>=2)
							{
								if(isEnpassantable&&r==4&&c==prvC-delta[boardChar[r][c]][i][j][2])moveType=4;
								else if(boardChar[r+delta[boardChar[r][c]][i][j][1]][c+delta[boardChar[r][c]][i][j][2]]==' ')break;
							}
						}
						if(boardChar[r][c]=='k')
						{
							if(i==9)
							{
								if(isPlayer1KingsideCastlingable)
								{
									canCastling=true;
									for(int k=0;k<=2;++k)if(isPlayer1Suiciding(r,c,0,k))canCastling=false;
									if(canCastling)moveType=5;
									else break;
								}
								else break;
							}
							if(i==10)
							{
								if(isPlayer1QueensideCastlingable)
								{
									canCastling=true;
									for(int k=0;k>=-2;--k)if(isPlayer1Suiciding(r,c,0,k))canCastling=false;
									if(canCastling)moveType=5;
									else break;
								}
								else break;
							}
						}
						if(boardChar[r][c]=='l')
						{
							if(i==9)
							{
								if(isPlayer2KingsideCastlingable)
								{
									canCastling=true;
									for(int k=0;k<=2;++k)if(isPlayer2Suiciding(r,c,0,k))canCastling=false;
									if(canCastling)moveType=5;
									else break;
								}
								else break;
							}
							if(i==10)
							{
								if(isPlayer2QueensideCastlingable)
								{
									canCastling=true;
									for(int k=0;k>=-2;--k)if(isPlayer2Suiciding(r,c,0,k))canCastling=false;
									if(canCastling)moveType=5;
									else break;
								}
								else break;
							}
						}
						if(nowTurn==1)
						{
							if(boardChar[r+delta[boardChar[r][c]][i][j][1]][c+delta[boardChar[r][c]][i][j][2]]=='r'||boardChar[r+delta[boardChar[r][c]][i][j][1]][c+delta[boardChar[r][c]][i][j][2]]=='n'||boardChar[r+delta[boardChar[r][c]][i][j][1]][c+delta[boardChar[r][c]][i][j][2]]=='b'||boardChar[r+delta[boardChar[r][c]][i][j][1]][c+delta[boardChar[r][c]][i][j][2]]=='q'||boardChar[r+delta[boardChar[r][c]][i][j][1]][c+delta[boardChar[r][c]][i][j][2]]=='k'||boardChar[r+delta[boardChar[r][c]][i][j][1]][c+delta[boardChar[r][c]][i][j][2]]=='p')break;
							if(boardChar[r+delta[boardChar[r][c]][i][j][1]][c+delta[boardChar[r][c]][i][j][2]]=='t'||boardChar[r+delta[boardChar[r][c]][i][j][1]][c+delta[boardChar[r][c]][i][j][2]]=='m'||boardChar[r+delta[boardChar[r][c]][i][j][1]][c+delta[boardChar[r][c]][i][j][2]]=='w'||boardChar[r+delta[boardChar[r][c]][i][j][1]][c+delta[boardChar[r][c]][i][j][2]]=='v'||boardChar[r+delta[boardChar[r][c]][i][j][1]][c+delta[boardChar[r][c]][i][j][2]]=='l'||boardChar[r+delta[boardChar[r][c]][i][j][1]][c+delta[boardChar[r][c]][i][j][2]]=='o')
							{
								breakFlag=true;
								moveType=4;
							}
						}
						if(nowTurn==2)
						{
							if(boardChar[r+delta[boardChar[r][c]][i][j][1]][c+delta[boardChar[r][c]][i][j][2]]=='t'||boardChar[r+delta[boardChar[r][c]][i][j][1]][c+delta[boardChar[r][c]][i][j][2]]=='m'||boardChar[r+delta[boardChar[r][c]][i][j][1]][c+delta[boardChar[r][c]][i][j][2]]=='w'||boardChar[r+delta[boardChar[r][c]][i][j][1]][c+delta[boardChar[r][c]][i][j][2]]=='v'||boardChar[r+delta[boardChar[r][c]][i][j][1]][c+delta[boardChar[r][c]][i][j][2]]=='l'||boardChar[r+delta[boardChar[r][c]][i][j][1]][c+delta[boardChar[r][c]][i][j][2]]=='o')break;
							if(boardChar[r+delta[boardChar[r][c]][i][j][1]][c+delta[boardChar[r][c]][i][j][2]]=='r'||boardChar[r+delta[boardChar[r][c]][i][j][1]][c+delta[boardChar[r][c]][i][j][2]]=='n'||boardChar[r+delta[boardChar[r][c]][i][j][1]][c+delta[boardChar[r][c]][i][j][2]]=='b'||boardChar[r+delta[boardChar[r][c]][i][j][1]][c+delta[boardChar[r][c]][i][j][2]]=='q'||boardChar[r+delta[boardChar[r][c]][i][j][1]][c+delta[boardChar[r][c]][i][j][2]]=='k'||boardChar[r+delta[boardChar[r][c]][i][j][1]][c+delta[boardChar[r][c]][i][j][2]]=='p')
							{
								breakFlag=true;
								moveType=4;
							}
						}
						if(nowTurn==1&&isPlayer1Suiciding(r,c,delta[boardChar[r][c]][i][j][1],delta[boardChar[r][c]][i][j][2]))continue;
						if(nowTurn==2&&isPlayer2Suiciding(r,c,delta[boardChar[r][c]][i][j][1],delta[boardChar[r][c]][i][j][2]))continue;
						canMove=true;
						isMovable[r+delta[boardChar[r][c]][i][j][1]][c+delta[boardChar[r][c]][i][j][2]]=true;
						boardButton[r+delta[boardChar[r][c]][i][j][1]][c+delta[boardChar[r][c]][i][j][2]].setBackgroundColor(color[moveType]);
						if(breakFlag)break;
					}
				}
				if(!canMove)return;
				prvR=r;
				prvC=c;
				boardButton[r][c].setBackgroundColor(color[2]);
				isFocused=true;
			}
			else
			{
				if(isMovable[r][c])
				{
					if(isEnpassantable&&c==prvC+delta[boardChar[prvR][prvC]][2][1][2])boardChar[prvR][prvC+delta[boardChar[prvR][prvC]][2][1][2]]=' ';
					if(isEnpassantable&&c==prvC+delta[boardChar[prvR][prvC]][3][1][2])boardChar[prvR][prvC+delta[boardChar[prvR][prvC]][3][1][2]]=' ';
					isEnpassantable=false;
					if(boardChar[prvR][prvC]=='p'&&prvR==2&&r==4)isEnpassantable=true;
					if(boardChar[prvR][prvC]=='o'&&prvR==7&&r==5)isEnpassantable=true;
					if(boardChar[prvR][prvC]=='k')
					{
						isPlayer1KingsideCastlingable=false;
						isPlayer1QueensideCastlingable=false;
					}
					if(boardChar[prvR][prvC]=='l')
					{
						isPlayer2KingsideCastlingable=false;
						isPlayer2QueensideCastlingable=false;
					}
					if(boardChar[prvR][prvC]=='r')
					{
						if(prvC==8)isPlayer1KingsideCastlingable=false;
						if(prvC==1)isPlayer1QueensideCastlingable=false;
					}
					if(boardChar[prvR][prvC]=='t')
					{
						if(prvC==8)isPlayer2KingsideCastlingable=false;
						if(prvC==1)isPlayer2QueensideCastlingable=false;
					}
					boardChar[r][c]=boardChar[prvR][prvC];
					boardChar[prvR][prvC]=' ';
					if(boardChar[r][c]=='k'&&r==prvR+delta[boardChar[r][c]][9][1][1]&&c==prvC+delta[boardChar[r][c]][9][1][2])
					{
						isPlayer1KingsideCastlingable=false;
						isPlayer1QueensideCastlingable=false;
						boardChar[r][c-1]='r';
						boardChar[r][c+1]=' ';
					}
					if(boardChar[r][c]=='k'&&r==prvR+delta[boardChar[r][c]][10][1][1]&&c==prvC+delta[boardChar[r][c]][10][1][2])
					{
						isPlayer1KingsideCastlingable=false;
						isPlayer1QueensideCastlingable=false;
						boardChar[r][c+1]='r';
						boardChar[r][c-2]=' ';
					}
					if(boardChar[r][c]=='l'&&r==prvR+delta[boardChar[r][c]][9][1][1]&&c==prvC+delta[boardChar[r][c]][9][1][2])
					{
						isPlayer2KingsideCastlingable=false;
						isPlayer2QueensideCastlingable=false;
						boardChar[r][c-1]='t';
						boardChar[r][c+1]=' ';
					}
					if(boardChar[r][c]=='l'&&r==prvR+delta[boardChar[r][c]][10][1][1]&&c==prvC+delta[boardChar[r][c]][10][1][2])
					{
						isPlayer2KingsideCastlingable=false;
						isPlayer2QueensideCastlingable=false;
						boardChar[r][c+1]='t';
						boardChar[r][c-2]=' ';
					}
					if(boardChar[r][c]=='p'&&r==8)
					{
						new AlertDialog.Builder(KennyChess.this).setTitle(getString(R.string.promotion_title)).setSingleChoiceItems(new String[]{getString(R.string.promotion_queen),getString(R.string.promotion_knight),getString(R.string.promotion_bishop),getString(R.string.promotion_rook)},0,new DialogInterface.OnClickListener()
						{
							public void onClick(DialogInterface dialog,int which)
							{
								mSelect=which;
							}
						}).setPositiveButton(android.R.string.ok,new DialogInterface.OnClickListener()
						{
							public void onClick(DialogInterface dialog,int which)
							{
								if(mSelect==0)boardChar[r][c]='q';
								if(mSelect==1)boardChar[r][c]='n';
								if(mSelect==2)boardChar[r][c]='b';
								if(mSelect==3)boardChar[r][c]='r';
								updateDisplay();
							}
						}).setNegativeButton(android.R.string.cancel,null).show().setCanceledOnTouchOutside(false);
					}
					if(boardChar[r][c]=='o'&&r==1)
					{
						new AlertDialog.Builder(KennyChess.this).setTitle(getString(R.string.promotion_title)).setSingleChoiceItems(new String[]{getString(R.string.promotion_queen),getString(R.string.promotion_knight),getString(R.string.promotion_bishop),getString(R.string.promotion_rook)},0,new DialogInterface.OnClickListener()
						{
							public void onClick(DialogInterface dialog,int which)
							{
								mSelect=which;
							}
						}).setPositiveButton(android.R.string.ok,new DialogInterface.OnClickListener()
						{
							public void onClick(DialogInterface dialog,int which)
							{
								if(mSelect==0)boardChar[r][c]='w';
								if(mSelect==1)boardChar[r][c]='m';
								if(mSelect==2)boardChar[r][c]='v';
								if(mSelect==3)boardChar[r][c]='t';
								updateDisplay();
							}
						}).setNegativeButton(android.R.string.cancel,null).show().setCanceledOnTouchOutside(false);
					}
					nowTurn=3-nowTurn;
					updateDisplay();
					isFocused=false;
				}
			}
		}
	};
}
